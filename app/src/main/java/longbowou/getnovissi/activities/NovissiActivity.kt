package longbowou.getnovissi.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_novissi.*
import kotlinx.android.synthetic.main.content_novissi.*
import longbowou.getnovissi.R
import longbowou.getnovissi.getNovissis
import longbowou.getnovissi.saveNovissis
import java.util.*

class NovissiActivity :
    AppCompatActivity() {

    private lateinit var oldNovissi: MutableMap<String, String>
    private var novissi: MutableMap<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novissi)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val novissis = getNovissis()

        if (intent.hasExtra(ARG_NOVISSI)) {
            val typeConverter = object : TypeToken<MutableMap<String, String>>() {}.type
            novissi = Gson().fromJson(intent.getStringExtra(ARG_NOVISSI), typeConverter)
            oldNovissi = novissi!!.toMutableMap()

            id_card.setText(novissi!!["id_card"])
            last_name.setText(novissi!!["last_name"])
            first_name.setText(novissi!!["first_name"])
            born_at.setText(novissi!!["born_at"])
            mother.setText(novissi!!["mother"])
            phone_number.setText(novissi!!["phone_number"])

            errors.setText(novissi!!["errors"])
            errors.visibility = View.VISIBLE

            processed.isChecked = novissi!!.containsKey("processed")
            processed.visibility = View.VISIBLE

            fab_delete.setOnClickListener {
                novissis.remove(novissi!!)
                saveNovissis(novissis)
                finish()
            }
            fab_delete.visibility = View.VISIBLE
        } else {
            id_card.addTextChangedListener {
                val searchFor = id_card.text.toString().trim()
                if (searchFor.isNotEmpty()) {
                    for (currentNovissi in novissis) {
                        if (currentNovissi["id_card"]!!.contains(searchFor, true)
                        ) {
                            id_card.error = "Id Card already exits"
                            break
                        }
                    }
                }
            }
            errors.visibility = View.GONE
            processed.visibility = View.GONE
            fab_delete.visibility = View.GONE
        }

        save.setOnClickListener {
            var isUpdate = true
            if (novissi == null) {
                novissi = mutableMapOf()
                isUpdate = false
            }

            novissi!!["id_card"] = id_card.text.toString().trim().toUpperCase(Locale.ROOT)
            novissi!!["last_name"] = last_name.text.toString().trim().toUpperCase(Locale.ROOT)
            novissi!!["first_name"] = first_name.text.toString().trim().toUpperCase(Locale.ROOT)
            novissi!!["born_at"] = born_at.text.toString().trim().toUpperCase(Locale.ROOT)
            novissi!!["mother"] = mother.text.toString().trim().toUpperCase(Locale.ROOT)
            novissi!!["phone_number"] = phone_number.text.toString().trim().toUpperCase(Locale.ROOT)

            if (phone_number.text.toString().isNotEmpty()) {
                novissi!!["errors"] = phone_number.text.toString().trim()
            } else {
                novissi!!.remove("errors")
            }

            if (processed.isChecked) {
                novissi!!["processed"] = "Yes"
            } else {
                novissi!!.remove("processed")
            }

            if (isUpdate) {
                val index = novissis.indexOf(oldNovissi)
                novissis[index] = novissi!!
            } else {
                novissis.add(novissi!!)
            }

            saveNovissis(novissis)
            finish()
        }
    }

    companion object {
        const val ARG_NOVISSI = "ARG_NOVISSI"
    }
}
