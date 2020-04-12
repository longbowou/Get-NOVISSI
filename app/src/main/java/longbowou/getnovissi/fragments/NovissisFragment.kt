package longbowou.getnovissi.fragments

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_novissis.view.*
import longbowou.getnovissi.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovissisFragment(private var processingFragment: ProcessingFragment) : Fragment() {

    lateinit var novissis: MutableList<MutableMap<String, String>>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_novissis, container, false)

        novissis = context!!.getNofissis()
        val adapter = NovissiAdapter(novissis)
        view?.recycle_view?.layoutManager = LinearLayoutManager(context)
        view?.recycle_view?.adapter = adapter

        view?.recycle_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (context as MainActivity).hideFloatingActionButtons()
                } else {
                    (context as MainActivity).showFloatingActionButtons()
                }
            }
        })

        val telephonyManager =
            context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val simList = mutableListOf<String>()
        val simCount = telephonyManager.phoneCount
        if (telephonyManager.phoneCount > 0) {
            for (item in 1..simCount) {
                simList.add("SIM $item")
            }
        }

        view.spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, simList)
        view.spinner.setSelection(ProcessNovissiAsyncTask.simSlot)
        view.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ProcessNovissiAsyncTask.simSlot = position
            }
        }

        view.search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val searchFor = view.search_edit_text.text
                if (searchFor.isNotEmpty()) {
                    val filterNovissis = mutableListOf<MutableMap<String, String>>()
                    for (novissi in novissis) {
                        if (novissi["id_card"]!!.contains(searchFor, true) ||
                            novissi["last_name"]!!.contains(searchFor, true) ||
                            novissi["first_name"]!!.contains(searchFor, true) ||
                            novissi["born_at"]!!.contains(searchFor, true) ||
                            novissi["mother"]!!.contains(searchFor, true) ||
                            novissi["phone_number"]!!.contains(searchFor, true)
                        ) {
                            filterNovissis.add(novissi)
                        }
                    }
                    adapter.update(filterNovissis)
                } else {
                    adapter.update(novissis)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        processingFragment.onDataUpdated = object : ProcessingFragment.OnDataUpdated {
            override fun onUpdate(newNovissis: MutableList<MutableMap<String, String>>) {
                novissis = newNovissis
                adapter.update(novissis)
            }
        }
        return view
    }
}
