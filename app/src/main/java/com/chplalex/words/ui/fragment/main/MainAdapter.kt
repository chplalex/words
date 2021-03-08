package com.chplalex.words.ui.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.model.data.DataModel
import com.chplalex.words.R

class MainAdapter(
    private val onListItemClickListener: OnListItemClickListener,
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var dataList: List<DataModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_main, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList.get(position))
    }

    override fun getItemCount() = dataList.size

    fun setData(dataList: List<DataModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val header = view.findViewById<TextView>(R.id.item_main_header)
        private val description = view.findViewById<TextView>(R.id.item_main_description)

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                header.text = data.word
                description.text = com.chplalex.utils.convertMeaningsToString(data.meanings!!)
                itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }

        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}