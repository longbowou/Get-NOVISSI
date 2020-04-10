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
        //BLANDES
        mutableMapOf(
            "id_card" to "1-108-03-00-99-01-01-00008",
            "last_name" to "BLANDES",
            "first_name" to "DANIEL LONGBOWOU",
            "born_at" to "03/04/1995",
            "mother" to "PAGUILA",
            "phone_number" to "96359792",
            "processed" to "Yes"
        ),
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
        mutableMapOf(
            "id_card" to "3-004-01-00-08-02-62-97227",
            "last_name" to "SALISSIBA",
            "first_name" to "KOSSIWA",
            "born_at" to "31/12/1998",
            "mother" to "OKPAPA",
            "phone_number" to "97344136"
        ),
        //TCHEPKASSI
        mutableMapOf(
            "id_card" to "3-004-01-00-01-01-02-00031",
            "last_name" to "TCHEKPASSI",
            "first_name" to "PALAKIYEM",
            "born_at" to "31/12/1966",
            "mother" to "KPAKPABIA",
            "phone_number" to "90077002"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-01-01-02-00092",
            "last_name" to "PIKILI",
            "first_name" to "MONDOMWEZOUE",
            "born_at" to "31/12/1968",
            "mother" to "BORO",
            "phone_number" to "96685211"
        ),
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
        mutableMapOf(
            "id_card" to "4-002-01-01-06-04-01-00800",
            "last_name" to "KIHEOU",
            "first_name" to "ESSO-REKE",
            "born_at" to "01/12/1986",
            "mother" to "LAOUSSIMASSI",
            "phone_number" to "91828225"
        ),
        //GRACE
        mutableMapOf(
            "id_card" to "3-004-01-00-26-01-70-80001",
            "last_name" to "AMANA",
            "first_name" to "KEMEALO",
            "born_at" to "13/01/1989",
            "mother" to "TOSSIME",
            "phone_number" to "91244723"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-26-03-83-69231",
            "last_name" to "AMANA",
            "first_name" to "PISSETIWE",
            "born_at" to "07/01/1997",
            "mother" to "TOSSIM",
            "phone_number" to "96919974"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-01-01-75-13201",
            "last_name" to "ALEME",
            "first_name" to "ANIKO",
            "born_at" to "31/12/1989",
            "mother" to "ATCHAFLO",
            "phone_number" to "90415127"
        ),
        //JOLIE
        mutableMapOf(
            "id_card" to "1-108-05-00-01-06-05-00750",
            "last_name" to "TEDE",
            "first_name" to "AFI JEANNE",
            "born_at" to "03/10/1997",
            "mother" to "SEGBADA",
            "phone_number" to "91313199"
        ),
        mutableMapOf(
            "id_card" to "1-108-05-00-01-04-02-00106",
            "last_name" to "SEGBADAN",
            "first_name" to "TCHESSOUN JULIE",
            "born_at" to "11/11/1997",
            "mother" to "SOFIADAN",
            "phone_number" to "98600969"
        ),
        //FJ
        mutableMapOf(
            "id_card" to "1-108-05-00-04-02-04-00673",
            "last_name" to "AGBANAGLO",
            "first_name" to "ABLA CLEMENTINE",
            "born_at" to "23/11/1996",
            "mother" to "AMETEKU",
            "phone_number" to "90329749"
        ),
        //JACOB
        mutableMapOf(
            "id_card" to "1-107-05-00-02-01-02-00740",
            "last_name" to "GBETANOU",
            "first_name" to "MAWULI",
            "born_at" to "23/01/1997",
            "mother" to "OWOUSSOU",
            "phone_number" to "92500365"
        ),
        mutableMapOf(
            "id_card" to "1-101-02-00-01-01-01-00086",
            "last_name" to "ADZOGENU",
            "first_name" to "KOMLA",
            "born_at" to "31/12/1991",
            "mother" to "AVONO",
            "phone_number" to "98926061"
        ),
        //FOLLY
        mutableMapOf(
            "id_card" to "1-108-05-00-04-02-18-02503",
            "last_name" to "N'TOUGLO",
            "first_name" to "AMIVI",
            "born_at" to "20/07/1989",
            "mother" to "FOLLY",
            "phone_number" to "91071570",
            "processed" to "Yes"
        ),
        mutableMapOf(
            "id_card" to "1-108-07-00-10-80-18-02599",
            "last_name" to "AKAKPO",
            "first_name" to "GBEMIHOEDE",
            "born_at" to "16/12/1981",
            "mother" to "KLOUSSA",
            "phone_number" to "90906834"
        ),
        mutableMapOf(
            "id_card" to "1-006-04-00-11-01-82-84001",
            "last_name" to "SEVI",
            "first_name" to "YAO A BERTRAND",
            "born_at" to "05/09/1996",
            "mother" to "FOLLY",
            "phone_number" to "97936857"
        ),
        //FLORENCE
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
        //ELI
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
        mutableMapOf(
            "id_card" to "1-107-01-00-12-03-01-00239",
            "last_name" to "BOSSRO",
            "first_name" to "ABLA",
            "born_at" to "31/12/1967",
            "mother" to "BOSSRO",
            "phone_number" to "91706639"
        ),
        //IT-PLEX
        mutableMapOf(
            "id_card" to "1-107-01-00-04-05-01-00479",
            "last_name" to "ADOYI",
            "first_name" to "ABDEL-M. BAYAILLE",
            "born_at" to "22/09/1979",
            "mother" to "ADAM",
            "phone_number" to "90992177"
        ),
        mutableMapOf(
            "id_card" to "2-001-01-00-01-01-01-00027",
            "last_name" to "KONDO",
            "first_name" to "SAMOUSSIYA",
            "born_at" to "04/01/1998",
            "mother" to "SALIFOU",
            "phone_number" to "93921341"
        ),
        mutableMapOf(
            "id_card" to "2-001-01-00-01-01-01-00026",
            "last_name" to "TCHA-TOURE",
            "first_name" to "AMDIATOU",
            "born_at" to "10/10/1996",
            "mother" to "MOUMOUNI",
            "phone_number" to "92067143"
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
        //SABINE
        mutableMapOf(
            "id_card" to "3-102-01-00-18-01-01-00309",
            "last_name" to "NABINE",
            "first_name" to "OUGMEELI SABINE",
            "born_at" to "25/08/1994",
            "mother" to "SEI",
            "phone_number" to "92567692"
        ),
        mutableMapOf(
            "id_card" to "3-004-01-00-07-01-01-00306",
            "last_name" to "NABINE",
            "first_name" to "OUNEEM JOEL",
            "born_at" to "23/06/2000",
            "mother" to "SEI",
            "phone_number" to "92260230"
        ),
        //REMY
        mutableMapOf(
            "id_card" to "1-107-04-00-08-01-01-00684",
            "last_name" to "SOKPOKOU",
            "first_name" to "AMA CELINE",
            "born_at" to "19/09/1998",
            "mother" to "DJOKPE",
            "phone_number" to "99278058"
        ),
        mutableMapOf(
            "id_card" to "1-107-02-00-05-01-02-00463",
            "last_name" to "ALAYI",
            "first_name" to "TCHILALO",
            "born_at" to "08/07/1977",
            "mother" to "DANDAJOU",
            "phone_number" to "79537054"
        ),
        //CENI
        mutableMapOf(
            "id_card" to "1-108-07-00-08-80-05-00020",
            "last_name" to "AGBEMENYA",
            "first_name" to "KOMI FRANC A",
            "born_at" to "25/04/1994",
            "mother" to "YAWLUI",
            "phone_number" to "92050769"
        ),
        mutableMapOf(
            "id_card" to "1-108-03-00-04-01-02-00075",
            "last_name" to "AMEDIN",
            "first_name" to "ESSI VERONIQUE SHALOM",
            "born_at" to "04/02/1996",
            "mother" to "AMEDIN",
            "phone_number" to "92280206"
        ),
        //OTHER
        mutableMapOf(
            "id_card" to "1-108-04-00-01-02-06-00366",
            "last_name" to "DJAHINI",
            "first_name" to "AFI",
            "born_at" to "04/02/1994",
            "mother" to "YAWOU",
            "phone_number" to "92089368"
        ),
        mutableMapOf(
            "id_card" to "1-108-01-00-06-01-33-02174",
            "last_name" to "GOMADO",
            "first_name" to "AYAO MAWULI STEPHANE",
            "born_at" to "18/11/1993",
            "mother" to "AKUE",
            "phone_number" to "99234554"
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
        ),
        mutableMapOf(
            "id_card" to "1-108-05-00-05-02-06-00626",
            "last_name" to "BANKAFE",
            "first_name" to "DORIS AFIAVI MARCELLE",
            "born_at" to "16/01/1998",
            "mother" to "SAGBO",
            "phone_number" to "92510674"
        ),
        mutableMapOf(
            "id_card" to "1-107-01-00-11-02-05-00790",
            "last_name" to "FIAFIATSI",
            "first_name" to "KOKOU FAFANE",
            "born_at" to "30/10/1991",
            "mother" to "EKLU",
            "phone_number" to "79475413"
        ),
        mutableMapOf(
            "id_card" to "1-108-02-00-07-06-94-31201",
            "last_name" to "KONLAMBIGUE",
            "first_name" to "MOIMOG",
            "born_at" to "23/05/1987",
            "mother" to "KONKADJA",
            "phone_number" to "90372816"
        ),
        mutableMapOf(
            "id_card" to "1-108-02-00-05-02-16-02704",
            "last_name" to "BOUKARI",
            "first_name" to "HAMDALATOU",
            "born_at" to "30/04/1995",
            "mother" to "ABOUMDOU",
            "phone_number" to "91524644"
        ),
        mutableMapOf(
            "id_card" to "1-108-05-00-01-06-04-00225",
            "last_name" to "KOUASSI",
            "first_name" to "GNANKAN",
            "born_at" to "21/05/1985",
            "mother" to "KOUSSANDJAN",
            "phone_number" to "90133410"
        ),
        mutableMapOf(
            "id_card" to "1-101-04-00-08-02-04-00358",
            "last_name" to "AROUNA",
            "first_name" to "GARBA",
            "born_at" to "07/01/1990",
            "mother" to "TANIMOU",
            "phone_number" to "90472253"
        ),
        mutableMapOf(
            "id_card" to "1-108-07-00-03-01-01-00115",
            "last_name" to "AYEWA",
            "first_name" to "DULILATOU",
            "born_at" to "23/05/1995",
            "mother" to "DIALLO",
            "phone_number" to "91354650"
        ),
        mutableMapOf(
            "id_card" to "1-108-05-00-09-01-03-00413",
            "last_name" to "BOATTENG",
            "first_name" to "AMA EMEFA",
            "born_at" to "28/12/1985",
            "mother" to "GBOUGBO",
            "phone_number" to "98992311"
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
