package longbowou.getnovissi

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_processing.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProcessingFragment : Fragment() {

    private val novissis = mutableListOf(
        //
        mutableMapOf(
            "id_card" to "3-004-01-00-08-03-05-00058",
            "last_name" to "PAGUILA SOLIGUE",
            "first_name" to "KEMEALO",
            "born_at" to "08/08/1968",
            "mother" to "KARABOU",
            "phone_number" to "90853180",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-08-03-05-0060",
            "last_name" to "BLANDES",
            "first_name" to "KODJO",
            "born_at" to "31/12/1965",
            "mother" to "OTCHONA",
            "phone_number" to "90930267",
            "processed" to "Yes"
        ),
        //
        mutableMapOf(
            "id_card" to "4-002-01-01-06-04-02-00998",
            "last_name" to "TCHEKPASSI",
            "first_name" to "ESSOHANA",
            "born_at" to "07/08/1990",
            "mother" to "PIKILI",
            "phone_number" to "93040504",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "1-108-02-00-03-02-03-00151",
            "last_name" to "TCHEKPASSI",
            "first_name" to "WARO GEDEON",
            "born_at" to "16/02/1995",
            "mother" to "PIKILI",
            "phone_number" to "90415828",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-01-02-01-00291",
            "last_name" to "TCHEKPASSI",
            "first_name" to "LOTIE GRACE",
            "born_at" to "31/10/1997",
            "mother" to "PIKILI",
            "phone_number" to "93269316",
            "processed" to "Yes"
        ),
        //
        mutableMapOf(
            "id_card" to "3-004-01-00-28-01-01-00662",
            "last_name" to "BAFAYA",
            "first_name" to "BATAMA MEGBIBA",
            "born_at" to "01/01/1966",
            "mother" to "TELA",
            "phone_number" to "90397647",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-28-01-02-00679",
            "last_name" to "BAFAYA",
            "first_name" to "WIEBA JEANNE",
            "born_at" to "02/11/1973",
            "mother" to "TELA",
            "phone_number" to "96395561",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "4-107-01-00-08-01-01-00166",
            "last_name" to "PITASSA",
            "first_name" to "ESSODOMNA-MARTINE",
            "born_at" to "04/07/1998",
            "mother" to "BADJOGLA",
            "phone_number" to "93798292",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-28-01-02-00599",
            "last_name" to "ATAMA",
            "first_name" to "MADI FLORENCE",
            "born_at" to "01/12/1998",
            "mother" to "BAFAYA",
            "phone_number" to "93010484",
            "processed" to "Yes"
        ),
        //
        mutableMapOf(
            "id_card" to "1-107-01-00-16-01-64-40001",
            "last_name" to "ADABRA",
            "first_name" to "KOKOU A ELIE",
            "born_at" to "17/11/1993",
            "mother" to "BOSSRO",
            "phone_number" to "91706639",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "1-107-01-00-16-01-64-40002",
            "last_name" to "ADABRA",
            "first_name" to "YAWA MARIE ROSE",
            "born_at" to "14/12/1995",
            "mother" to "BOSSRO",
            "phone_number" to "70382120",
            "processed" to "Yes"
        ),
        //
        mutableMapOf(
            "id_card" to "1-107-01-00-04-05-01-00479",
            "last_name" to "ADOYI",
            "first_name" to "ABDEL-M BAYAILL",
            "born_at" to "22/09/1979",
            "mother" to "ADAM",
            "phone_number" to "90992177"
        ),
        mutableMapOf(
            "id_card" to "1-108-01-06-01-33-02174",
            "last_name" to "GOMADO",
            "first_name" to "AYAO MAWULI STEPHANE",
            "born_at" to "18/11/1993",
            "mother" to "AKUE",
            "phone_number" to "99234554"
        ),
        mutableMapOf(
            "id_card" to "2-001-01-00-07-03-04-00617",
            "last_name" to "OGBONE",
            "first_name" to "IREDON  ARIF",
            "born_at" to "22/09/1998",
            "mother" to "GOUNI",
            "phone_number" to "92533594",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "1-107-01-00-01-01-17-02651",
            "last_name" to "ABI-ABRANGAO",
            "first_name" to "SAMOUSSIDINE",
            "born_at" to "31/12/1999",
            "mother" to "ABDOULAYE",
            "phone_number" to "92851510",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "1-108-05-00-02-99-06-00185",
            "last_name" to "PARI",
            "first_name" to "PGNOSSI OLIVIER",
            "born_at" to "31/05/1994",
            "mother" to "TARETETO",
            "phone_number" to "91523550"
        ),
        mutableMapOf(
            "id_card" to "1-108-07-00-08-3-01-00888",
            "last_name" to "WOROU",
            "first_name" to "AYIME ANASS",
            "born_at" to "16/01/1994",
            "mother" to "TARETETO",
            "phone_number" to "90643881"
        )
    )
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
    }

    fun launchNovissiProcessing(novissi: MutableMap<String, String>? = null) {
        var index = 0
        if (novissi !== null) {
            index = novissis.indexOf(novissi)
            index++
        }

        if (index >= novissis.count()) {
            return
        }

        var nextNovissi = novissis[index]
        while (true) {
            if (nextNovissi["processed"] == null) {
                updateUi(nextNovissi)
                processNovissi(nextNovissi, map)
                break
            }

            index++
            if (index >= novissis.count()) {
                break
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
        val async = Async(context!!, map)
        async.asyncInterface = object : Async.AsyncInterface {
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
            async.execute(novissi)
        }
    }
}
