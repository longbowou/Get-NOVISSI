package longbowou.getnovissi.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_processing.*
import longbowou.getnovissi.ProcessNovissiAsyncTask
import longbowou.getnovissi.R
import longbowou.getnovissi.getNovissis
import longbowou.getnovissi.saveNovissis

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProcessingFragment : Fragment() {

    lateinit var novissis: MutableList<MutableMap<String, String>>
    private var isProcessing = false
    private var delay = 3000L
    var onDataUpdated: OnDataUpdated? = null
    private val map = HashMap<String, java.util.HashSet<String>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map["KEY_LOGIN"] = HashSet(listOf())
        map["KEY_ERROR"] = HashSet(listOf())

        novissis = context!!.getNovissis()
    }

    private fun saveNovissis() {
        context?.saveNovissis(novissis)
        onDataUpdated?.onUpdate(novissis)
    }

    fun launchNovissiProcessing(novissi: MutableMap<String, String>? = null) {
        if (isProcessing) {
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
            saveNovissis()
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

    private fun processNovissi(
        novissi: MutableMap<String, String>,
        map: java.util.HashMap<String, java.util.HashSet<String>>
    ) {
        val async = ProcessNovissiAsyncTask(context!!, map)
        async.asyncInterface = object :
            ProcessNovissiAsyncTask.AsyncInterface {
            override fun onUpdate(step: String, message: String) {
                isProcessing = false
                step_textview.text = step
            }

            override fun onProcessed(novissi: MutableMap<String, String>) {
                isProcessing = false
                updateUi(isRestarting = true)
                Handler().postDelayed({
                    launchNovissiProcessing(novissi)
                }, delay)
            }

            override fun onError(novissi: MutableMap<String, String>) {
                isProcessing = false
                updateUi(isRestarting = true)
                Handler().postDelayed({
                    processNovissi(novissi, map)
                }, delay)
            }
        }

        if (!isProcessing) {
            isProcessing = true
            updateUi(novissi)
            async.execute(novissi)
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

    interface OnDataUpdated {
        fun onUpdate(novissis: MutableList<MutableMap<String, String>>)
    }

    companion object {
        const val NOVISSIS = "NOVISSIS"
        val TAG = ProcessingFragment::class.java.simpleName
    }
}
