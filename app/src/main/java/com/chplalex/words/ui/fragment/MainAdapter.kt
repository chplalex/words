package com.chplalex.words.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.words.R
import com.chplalex.words.mvp.model.DataModel

class MainAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private val dataList: List<DataModel>
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_main, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList.get(position))
    }

    override fun getItemCount() = dataList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val header = view.findViewById<TextView>(R.id.item_main_header)
        private val description = view.findViewById<TextView>(R.id.item_main_description)

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                header.text = data.word
                description.text = data.meanings?.get(0)?.translation?.text

                itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}