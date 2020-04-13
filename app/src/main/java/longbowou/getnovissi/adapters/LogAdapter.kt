package longbowou.getnovissi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_log.view.*
import longbowou.getnovissi.R

class LogAdapter :
    RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    private var logs: List<Map<String, String>> = listOf()

    class LogViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_log, parent, false)

        return LogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return logs.count()
    }

    fun update(logs: List<Map<String, String>>) {
        this.logs = logs
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.itemView.log.text = logs[position]["content"]

        when (logs[position]["level"]) {
            ERROR -> {
                holder.itemView.log.setTextColor(holder.itemView.context.getColor(R.color.red))
            }

            SUCCESS -> {
                holder.itemView.log.setTextColor(holder.itemView.context.getColor(R.color.green))
            }

            WARNING -> {
                holder.itemView.log.setTextColor(holder.itemView.context.getColor(R.color.yellow))
            }

            else -> {
                holder.itemView.log.setTextColor(holder.itemView.context.getColor(R.color.colorPrimary))
            }
        }
    }

    companion object {
        const val ERROR = "ERROR"
        const val SUCCESS = "SUCCESS"
        const val DEBUG = "DEBUG"
        const val WARNING = "WARNING"
    }
}