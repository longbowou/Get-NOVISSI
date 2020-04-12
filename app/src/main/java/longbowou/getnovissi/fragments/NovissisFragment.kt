package longbowou.getnovissi.fragments

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_novissis.*
import kotlinx.android.synthetic.main.fragment_novissis.view.*
import longbowou.getnovissi.NovissiAdapter
import longbowou.getnovissi.ProcessNovissiAsyncTask
import longbowou.getnovissi.R
import longbowou.getnovissi.activities.MainActivity
import longbowou.getnovissi.getNovissis


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovissisFragment : Fragment() {

    lateinit var novissis: MutableList<MutableMap<String, String>>
    private var novissiAdapter: NovissiAdapter? = null
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_novissis, container, false)

        novissis = context!!.getNovissis()
        novissiAdapter = NovissiAdapter(novissis)
        fragmentView.recycle_view?.layoutManager = LinearLayoutManager(context)
        fragmentView.recycle_view?.adapter = novissiAdapter

        fragmentView.textview_first.text = "Novissis (${novissis.count()})"

        fragmentView.recycle_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        fragmentView.spinner.adapter =
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, simList)
        fragmentView.spinner.setSelection(ProcessNovissiAsyncTask.simSlot)
        fragmentView.spinner.onItemSelectedListener = object : OnItemSelectedListener {
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

        fragmentView.search_edit_text.addTextChangedListener {
            filterNovissis()
        }

        fragmentView.unprocessed_checkBox.setOnCheckedChangeListener { _, _ ->
            filterNovissis()
        }

        fragmentView.swipe_refresh.setOnRefreshListener {
            novissis = context!!.getNovissis()
            filterNovissis()
            swipe_refresh.isRefreshing = false
        }

        return fragmentView
    }

    private fun filterNovissis() {
        val searchFor = fragmentView.search_edit_text.text.toString().trim()
        if (searchFor.isNotEmpty()) {
            val filterNovissis = mutableListOf<MutableMap<String, String>>()
            for (novissi in novissis) {
                if (fragmentView.unprocessed_checkBox.isChecked) {
                    if (!novissi.contains("processed") &&
                        novissi["id_card"]!!.contains(searchFor, true) ||
                        !novissi.contains("processed") &&
                        novissi["last_name"]!!.contains(searchFor, true) ||
                        !novissi.contains("processed") &&
                        novissi["first_name"]!!.contains(searchFor, true) ||
                        !novissi.contains("processed") &&
                        novissi["born_at"]!!.contains(searchFor, true) ||
                        !novissi.contains("processed") &&
                        novissi["mother"]!!.contains(searchFor, true) ||
                        novissi["phone_number"]!!.contains(searchFor, true)
                    ) {
                        filterNovissis.add(novissi)
                    }
                } else {
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
            }
            novissiAdapter?.update(filterNovissis)
        } else {
            if (fragmentView.unprocessed_checkBox.isChecked) {
                val filterNovissis = mutableListOf<MutableMap<String, String>>()
                for (novissi in novissis) {
                    if (!novissi.containsKey("processed")) {
                        filterNovissis.add(novissi)
                    }
                }
                fragmentView.textview_first.text = "Novissis (${filterNovissis.count()})"
                novissiAdapter?.update(filterNovissis)
            } else {
                fragmentView.textview_first.text = "Novissis (${novissis.count()})"
                novissiAdapter?.update(novissis)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        novissis = context!!.getNovissis()
        filterNovissis()
    }
}
