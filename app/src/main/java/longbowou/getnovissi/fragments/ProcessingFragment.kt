package longbowou.getnovissi.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_processing.*
import kotlinx.android.synthetic.main.fragment_processing.view.*
import longbowou.getnovissi.ProcessNovissiAsyncTask
import longbowou.getnovissi.R
import longbowou.getnovissi.adapters.LogAdapter
import longbowou.getnovissi.getNovissis
import longbowou.getnovissi.saveNovissis
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProcessingFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var novissis: MutableList<MutableMap<String, String>>
    private lateinit var logAdapter: LogAdapter
    private var novissiAsyncTask: ProcessNovissiAsyncTask? = null
    private var isProcessing = false
    private var isRunning = false
    private var delay = 3000L
    private val map = HashMap<String, java.util.HashSet<String>>()
    private var logs: MutableList<Map<String, String>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_processing, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map["KEY_LOGIN"] = HashSet(listOf())
        map["KEY_ERROR"] = HashSet(listOf())

        novissis = context!!.getNovissis()

        logAdapter = LogAdapter()
        fragmentView.log_recycler_view.layoutManager = LinearLayoutManager(fragmentView.context)
        fragmentView.log_recycler_view.adapter = logAdapter
    }

    private fun startNovissiProcessing(
        novissi: MutableMap<String, String>? = null,
        startRunning: Boolean = false
    ) {
        novissis = context!!.getNovissis()

        if (startRunning) {
            isRunning = true
        }

        if (!isRunning || isProcessing) {
            updateUi()
            return
        }

        var index = 0

        val unproccedNovissi = findUnprocessed()
        if (unproccedNovissi !== null) {
            processNovissi(unproccedNovissi, map)
            return
        }

        if (novissi !== null) {
            index = novissis.indexOf(novissi)
            index++
        }

        if (index >= novissis.count()) {
            updateUi()
            return
        }

        var nextNovissi = novissis[index]
        while (true) {
            if (nextNovissi["processed"] == null) {
                processNovissi(nextNovissi, map)
                return
            }

            index++
            if (index >= novissis.count()) {
                updateUi()
                return
            }
            nextNovissi = novissis[index]
        }
    }

    private fun updateUi(
        novissi: MutableMap<String, String>? = null,
        isRestarting: Boolean = false
    ) {
        if (novissi != null) {
            id_card.text = novissi["id_card"]
            last_name.text = novissi["last_name"]
            first_name.text = novissi["first_name"]
            born_at.text = novissi["born_at"]
            mother.text = novissi["mother"]
            phone_number.text = novissi["phone_number"]
            step_textview.text = getString(R.string.init)
            novissi_details_layout.visibility = View.VISIBLE
            empty_layout.visibility = View.GONE
        } else {
            id_card.text = null
            last_name.text = null
            first_name.text = null
            born_at.text = null
            mother.text = null
            phone_number.text = null
            step_textview.text = if (isRestarting) getString(R.string.restarting) else null
            novissi_details_layout.visibility = View.GONE
            empty_layout.visibility = View.VISIBLE
        }
    }

    private fun updateLogs(level: String, messages: List<String?>) {
        val c = Calendar.getInstance()
        val prefix = String.format("%tD %tT", c, c)
        for (message in messages) {
            if (message != null) {
                logs.add(mapOf("level" to level, "content" to "$prefix $message"))
            }
        }
        logAdapter.update(logs)
        fragmentView.log_recycler_view.scrollToPosition(logs.count() - 1)
        fragmentView.log_textView.text = "Log (${logs.count()})"

    }

    private fun processNovissi(
        novissi: MutableMap<String, String>,
        map: java.util.HashMap<String, java.util.HashSet<String>>
    ) {
        novissiAsyncTask = ProcessNovissiAsyncTask(context!!, map)
        novissiAsyncTask?.asyncInterface = object :
            ProcessNovissiAsyncTask.AsyncInterface {
            override fun onUpdate(step: String, message: String?, isWarning: Boolean) {
                isProcessing = false
                step_textview.text = step

                updateLogs(
                    if (isWarning) LogAdapter.WARNING else LogAdapter.DEBUG,
                    listOf(step, message)
                )
            }

            override fun onProcessed(
                novissi: MutableMap<String, String>,
                step: String,
                message: String
            ) {
                isProcessing = false
                updateUi(isRestarting = true)
                context?.saveNovissis(novissis)
                Handler().postDelayed({
                    startNovissiProcessing(novissi)
                }, delay)

                updateLogs(
                    if (novissi.containsKey("errors")) LogAdapter.ERROR else LogAdapter.SUCCESS,
                    listOf(step, message)
                )
            }

            override fun onError(
                novissi: MutableMap<String, String>,
                step: String,
                message: String
            ) {
                isProcessing = false
                updateUi(isRestarting = true)
                Handler().postDelayed({
                    processNovissi(novissi, map)
                }, delay)

                updateLogs(LogAdapter.DEBUG, listOf(step, message))
            }
        }

        if (!isRunning || !isProcessing) {
            isProcessing = true
            updateUi(novissi)
            novissiAsyncTask?.execute(novissi)
        } else {
            updateUi()
        }
    }

    private fun findUnprocessed(): MutableMap<String, String>? {
        for (novissi in novissis) {
            if (novissi["processed"] == null && novissi["errors"] == null) {
                return novissi
            }
        }
        return null
    }

    fun stop() {
        if (isRunning) {
            step_textview.text = getString(R.string.stopping)
            isRunning = false
            novissiAsyncTask?.cancel(true)
        } else {
            Toast.makeText(context, "Already Stopped", Toast.LENGTH_LONG).show()
        }
    }

    fun start() {
        if (!isRunning) {
            isRunning = true
            startNovissiProcessing(startRunning = true)
        } else {
            Toast.makeText(context, "Already Started", Toast.LENGTH_LONG).show()
        }
    }
}
