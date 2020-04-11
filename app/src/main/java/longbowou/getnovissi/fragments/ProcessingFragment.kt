package longbowou.getnovissi.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_processing.*
import longbowou.getnovissi.ProcessNovissiAsyncTask
import longbowou.getnovissi.R
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProcessingFragment : Fragment() {

    private lateinit var novissis: MutableList<MutableMap<String, String>>
    private var isProcessing = false
    private var delay = 2000L

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

        val defaultNovissis =
            context?.assets?.open("novissis.json")?.bufferedReader().use { it?.readText() }
        val savedNovissis = context?.getSharedPreferences(
            NOVISSIS,
            Context.MODE_PRIVATE
        )?.getString(NOVISSIS, defaultNovissis)
        val typeConverter = object : TypeToken<MutableList<MutableMap<String, String>>>() {}.type
        novissis = Gson().fromJson(savedNovissis, typeConverter)

        if (savedNovissis != null) {
            loggSavedNovissis(savedNovissis)
        }
    }

    private fun loggSavedNovissis(novissis: String) {
        Log.d(TAG, "Saved Novissis")
        val maxLenth = 4000
        if (novissis.length > maxLenth) {
            val chunkCount: Int = novissis.length / maxLenth // integer division
            for (i in 0..chunkCount) {
                val max = maxLenth * (i + 1)
                if (max >= novissis.length) {
                    Log.d(
                        TAG,
                        novissis.substring(maxLenth * i)
                    )
                } else {
                    Log.d(
                        TAG,
                        novissis.substring(maxLenth * i, max)
                    )
                }
            }
        } else {
            Log.d(TAG, novissis)
        }
    }

    private fun saveNovissis() {
        val novissisJson = Gson().toJson(novissis)
        context?.getSharedPreferences(NOVISSIS, Context.MODE_PRIVATE)
            ?.edit()?.putString(NOVISSIS, novissisJson)
            ?.apply()
        val novissisJsonFile = File(context?.filesDir, "novissis.json")
        novissisJsonFile.writeText(novissisJson)
    }

    fun launchNovissiProcessing(novissi: MutableMap<String, String>? = null) {
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
                return
            }
            nextNovissi = novissis[index]
        }
    }

    private fun updateUi(novissi: MutableMap<String, String>) {
        id_card.text = novissi["id_card"]
        last_name.text = novissi["last_name"]
        first_name.text = novissi["first_name"]
        born_at.text = novissi["born_at"]
        mother.text = novissi["mother"]
        phone_number.text = novissi["phone_number"]
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
                step_textview.text = getString(R.string.restarting)
                Handler().postDelayed({
                    launchNovissiProcessing(novissi)
                }, delay)
            }

            override fun onError(novissi: MutableMap<String, String>) {
                isProcessing = false
                step_textview.text = getString(R.string.restarting)
                Handler().postDelayed({
                    processNovissi(novissi, map)
                }, delay)
            }
        }

        if (!isProcessing) {
            isProcessing = true
            step_textview.text = getString(R.string.init)
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

    companion object {
        const val NOVISSIS = "NOVISSIS"
        val TAG = ProcessingFragment::class.java.simpleName
    }
}
