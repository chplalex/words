package com.chplalex.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.model.ui.DataModel
import com.chplalex.utils.ui.viewById

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val header by viewById<TextView>(R.id.item_history_header)

        fun bind(data: DataModel) {
            if (layoutPosition == RecyclerView.NO_POSITION) return
            header.text = data.word
            header.setOnClickListener {
                Toast.makeText(itemView.context, "on click: ${data.word}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}