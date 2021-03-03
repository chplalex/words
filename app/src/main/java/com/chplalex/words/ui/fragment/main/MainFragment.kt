package com.chplalex.words.ui.fragment.main

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.words.R
import com.chplalex.words.isOnline
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.data.description
import com.chplalex.words.model.data.imageUrl
import com.chplalex.words.ui.fragment.base.BaseFragment
import com.chplalex.words.ui.fragment.search.SearchFragment
import com.chplalex.words.ui.fragment.search.SearchFragment.Companion.SEARCH_FRAGMENT_TAG
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : BaseFragment<AppState, MainInteractor>(R.layout.fragment_main) {

    private val navController by inject<NavController> { parametersOf(requireActivity()) }

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    override lateinit var model: MainViewModel

    override val titleRes = R.string.title_main

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {

        override fun onItemClick(data: DataModel) {
            val action = MainFragmentDirections.actionMainToDescription(
                word = data.word,
                description = data.description(),
                imageUrl = data.imageUrl()
            )
            navController.navigate(action)
        }
    }

    private val onSearchClickListener = object : SearchFragment.OnSearchClickListener {

        override fun onClick(searchWord: String) {
            isNetworkAviable = isOnline(requireContext())
            model.getData(searchWord, isNetworkAviable)
            if (!isNetworkAviable) {
                showNoInternetConnectionDialog()
            }
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