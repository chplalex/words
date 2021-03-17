package com.chplalex.history

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.base.BaseFragment
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import org.koin.android.scope.currentScope

class HistoryFragment : BaseFragment<AppState, HistoryInteractor>(R.layout.fragment_history) {

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override lateinit var model: HistoryViewModel

    override val titleRes = R.string.title_history
    override val layoutWorkingRes = R.id.layout_working

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun initViewModel() {
        injectDependencies()

        val viewModel: HistoryViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this, { renderData(it) })
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