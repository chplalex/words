package com.chplalex.main.main

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.main.R
import com.chplalex.main.search.SearchFragment
import com.chplalex.main.search.SearchFragment.Companion.SEARCH_FRAGMENT_TAG
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.model.data.description
import com.chplalex.model.data.imageUrl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : com.chplalex.base.BaseFragment<AppState, MainInteractor>(R.layout.fragment_main) {

    private val navController by inject<NavController> { parametersOf(requireActivity()) }

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    override lateinit var model: MainViewModel

    @StringRes
    override val titleRes = R.string.title_main

    @IdRes
    override val layoutWorkingRes = R.id.layout_working

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {

        override fun onItemClick(data: DataModel) {
            navController.navigate(
                MainFragmentDirections.actionMainToDescription(
                    data.word,
                    data.description(),
                    data.imageUrl()
                )
            )
        }
    }

    private val onSearchClickListener = object : SearchFragment.OnSearchClickListener {

        override fun onClick(searchWord: String) {
            model.getData(searchWord, isNetworkAvailable)
        }
    }

    private val onFabOnClickListener = View.OnClickListener {
        SearchFragment.newInstance().also {
            it.setOnSearchClickListener(onSearchClickListener)
            it.show(parentFragmentManager, SEARCH_FRAGMENT_TAG)
        }
    }

    override fun initViewModel() {
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this, Observer<AppState> { renderData(it) })
    }

    override fun initViews(view: View) {
        super.initViews(view)

        view.findViewById<FloatingActionButton>(R.id.fab_search).setOnClickListener(onFabOnClickListener)

        view.findViewById<RecyclerView>(R.id.recycler_view).also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }
}