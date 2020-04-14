package longbowou.getnovissi.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_novissi.view.*
import longbowou.getnovissi.R
import longbowou.getnovissi.activities.NovissiActivity

class NovissiAdapter(private var novissis: MutableList<MutableMap<String, String>>) :
    RecyclerView.Adapter<NovissiAdapter.NovissiViewHolder>() {

    class NovissiViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovissiViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_novissi, parent, false)

        return NovissiViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return novissis.count()
    }

    fun update(novissis: MutableList<MutableMap<String, String>>) {
        this.novissis = novissis
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NovissiViewHolder, position: Int) {
        val novissi = novissis[position]

        holder.itemView.id_card.text = novissi["id_card"]
        holder.itemView.last_name_.text = novissi["last_name"]
        holder.itemView.first_name.text = novissi["first_name"]
        holder.itemView.born_at.text = novissi["born_at"]
        holder.itemView.mother.text = novissi["mother"]
        holder.itemView.phone_number.text = novissi["phone_number"]

        if (novissi.containsKey("processed")) {
            holder.itemView.phone_number.setTextColor(
                holder.itemView.context.getColor(
                    R.color.green
                )
            )
        } else if (novissi.containsKey("errors") && novissi["errors"]!!.contains("phone_number")) {
            holder.itemView.phone_number.setTextColor(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
        } else {
            holder.itemView.phone_number.setTextColor(
                holder.itemView.context.getColor(
                    R.color.colorPrimary
                )
            )
        }

        if (novissi.containsKey("errors") && novissi["errors"]!!.contains("id_card")) {
            holder.itemView.id_card.setTextColor(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
        } else {
            holder.itemView.id_card.setTextColor(
                holder.itemView.context.getColor(
                    R.color.colorPrimary
                )
            )
        }

        if (novissi.containsKey("errors") && novissi["errors"]!!.contains("last_name")) {
            holder.itemView.last_name_.setTextColor(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
        } else {
            holder.itemView.last_name_.setTextColor(
                holder.itemView.context.getColor(
                    R.color.colorPrimary
                )
            )
        }

        if (novissi.containsKey("errors") && novissi["errors"]!!.contains("first_name")) {
            holder.itemView.first_name.setTextColor(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
        } else {
            holder.itemView.first_name.setTextColor(
                holder.itemView.context.getColor(
                    R.color.colorPrimary
                )
            )
        }

        if (novissi.containsKey("errors") && novissi["errors"]!!.contains("born_at")) {
            holder.itemView.born_at.setTextColor(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
        } else {
            holder.itemView.born_at.setTextColor(
                holder.itemView.context.getColor(
                    R.color.colorPrimary
                )
            )
        }

        if (novissi.containsKey("errors") && novissi["errors"]!!.contains("mother")) {
            holder.itemView.mother.setTextColor(
                holder.itemView.context.getColor(
                    R.color.red
                )
            )
        } else {
            holder.itemView.mother.setTextColor(
                holder.itemView.context.getColor(
                    R.color.colorPrimary
                )
            )
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NovissiActivity::class.java)
            intent.putExtra(NovissiActivity.ARG_NOVISSI, Gson().toJson(novissi))
            holder.itemView.context.startActivity(intent)
        }
    }
}