package com.chplalex.words.ui.fragment.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.words.R
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.ui.fragment.base.BaseFragment
import com.chplalex.words.viewmodel.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment<AppState, HistoryInteractor>(R.layout.fragment_history) {

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override lateinit var model: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this, Observer<AppState> { renderData(it) })
    }

    override fun initViews(view: View) {
        super.initViews(view)

        view.findViewById<RecyclerView>(R.id.fragment_history_recyclerview).also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }
}