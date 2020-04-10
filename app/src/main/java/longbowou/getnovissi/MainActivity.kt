package longbowou.getnovissi

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.romellfudi.ussdlibrary.USSDController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val novissis = mutableListOf(
        mutableMapOf(
            "id_card" to "2-001-01-00-07-03-04-00617",
            "last_name" to "OGBONE",
            "first_name" to "IREDON  ARIF",
            "born_at" to "22/09/1998",
            "mother" to "GOUNI",
            "phone_number" to "92533594"
        )
    )
    private var isProcessing = false
    private var delay = 2000L

    private val map = HashMap<String, java.util.HashSet<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        map["KEY_LOGIN"] = HashSet(listOf())
        map["KEY_ERROR"] = HashSet(listOf())

        USSDController.verifyAccesibilityAccess(this)
        USSDController.verifyOverLay(this)

        fab.setOnClickListener { view ->
            launchNovissiProcessing()
        }
    }

    private fun launchNovissiProcessing(novissi: MutableMap<String, String>? = null) {
        var index = 0
        if (novissi !== null) {
            index = novissis.indexOf(novissi)
            index++
        }

        if (index < novissis.count()) {
            println("Launching novissis $novissis")
            processNovissi(novissis[index], map)
        }
    }

    private fun processNovissi(
        novissi: MutableMap<String, String>,
        map: java.util.HashMap<String, java.util.HashSet<String>>
    ) {
        if (novissi["processed"] !== null) {
            launchNovissiProcessing(novissi)
        }

        val async = Async(applicationContext, map)
        async.asyncInterface = object : Async.AsyncInterface {
            override fun onUpdate(step: String, message: String) {
                isProcessing = false
            }

            override fun onProcessed(novissi: MutableMap<String, String>) {
                isProcessing = false
                Handler().postDelayed({
                    launchNovissiProcessing(novissi)
                }, delay)
            }

            override fun onError(novissi: MutableMap<String, String>) {
                isProcessing = false
                Handler().postDelayed({
                    processNovissi(novissi, map)
                }, delay)
            }
        }

        if (!isProcessing) {
            isProcessing = true
            async.execute(novissi)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
