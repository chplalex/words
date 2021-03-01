package com.chplalex.words.ui.fragment.main

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.words.R
import com.chplalex.words.makeGone
import com.chplalex.words.makeVisible
import com.chplalex.words.isOnline
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.datasource.MainInteractor
import com.chplalex.words.ui.fragment.base.BaseFragment
import com.chplalex.words.ui.fragment.description.DescriptionFragment
import com.chplalex.words.ui.fragment.search.SearchFragment
import com.chplalex.words.ui.fragment.search.SearchFragment.Companion.SEARCH_FRAGMENT_TAG
import com.chplalex.words.utils.convertMeaningsToString
import com.chplalex.words.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<AppState, MainInteractor>(R.layout.fragment_main) {

    private lateinit var progressBarHorizontal: ProgressBar
    private lateinit var progressBarRound: ProgressBar
    private lateinit var layoutWorking: FrameLayout
    private lateinit var layoutLoading: FrameLayout

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    override lateinit var model: MainViewModel

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {

        override fun onItemClick(data: DataModel) {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container, DescriptionFragment.newInstance(data.word, convertMeaningsToString(data.meanings!!), data.meanings[0].imageUrl))
                .commit()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViewModel() {
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this, Observer<AppState> { renderData(it) })
    }

    private fun initViews(view: View) {
        view.findViewById<FloatingActionButton>(R.id.fab_search).setOnClickListener(onFabOnClickListener)

        view.findViewById<RecyclerView>(R.id.recycler_view).also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }

        progressBarHorizontal = view.findViewById(R.id.progress_bar_horizontal)
        progressBarRound = view.findViewById(R.id.progress_bar_round)
        layoutWorking = view.findViewById(R.id.layout_working)
        layoutLoading = view.findViewById(R.id.layout_loading)
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showWorking()
                val dataModel = appState.data
                if (dataModel.isNullOrEmpty()) {
                    showAlertDialog(
                        getString(R.string.dialog_tittle_sorry),
                        getString(R.string.empty_server_response_on_success))
                } else {
                    showWorking()
                    adapter.setData(dataModel)
                }
            }
            is AppState.Loading -> {
                showLoading()
                if (appState.progress == null) {
                    progressBarRound.makeVisible()
                    progressBarHorizontal.makeGone()
                } else {
                    progressBarRound.makeGone()
                    progressBarHorizontal.makeVisible()
                    progressBarHorizontal.progress = appState.progress
                }
            }
            is AppState.Error -> {
                showWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    private fun showWorking() {
        layoutWorking.makeVisible()
        layoutLoading.makeGone()
    }

    private fun showLoading() {
        layoutWorking.makeGone()
        layoutLoading.makeVisible()
    }
}