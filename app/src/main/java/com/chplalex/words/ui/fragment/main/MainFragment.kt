package com.chplalex.words.ui.fragment.main

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chplalex.words.R
import com.chplalex.words.makeGone
import com.chplalex.words.makeVisible
import com.chplalex.words.mvp.contract.IPresenter
import com.chplalex.words.mvp.contract.IView
import com.chplalex.words.mvp.model.data.AppState
import com.chplalex.words.mvp.model.data.DataModel
import com.chplalex.words.mvp.presenter.MainPresenterImlp
import com.chplalex.words.ui.fragment.base.BaseFragment
import com.chplalex.words.ui.fragment.search.SearchFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : BaseFragment<AppState>(R.layout.fragment_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBarHorizontal: ProgressBar
    private lateinit var progressBarRound: ProgressBar
    private lateinit var layoutSuccess: FrameLayout
    private lateinit var layoutLoading: FrameLayout
    private lateinit var layoutError: LinearLayout
    private lateinit var textError: TextView
    private lateinit var buttonReload: MaterialButton

    private var adapter: MainAdapter? = null

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            Toast.makeText(context, data.word, Toast.LENGTH_SHORT).show()
        }
    }

    private val onSearchClickListener = object : SearchFragment.OnSearchClickListener {
        override fun onClick(searchWord: String) {
            presenter.getData(searchWord, true)
        }
    }

    private val onFabOnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            SearchFragment.newInstance().also {
                it.setOnSearchClickListener(onSearchClickListener)
                it.show(requireFragmentManager(), null)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.fab_search).setOnClickListener(onFabOnClickListener)

        recyclerView = view.findViewById(R.id.recycler_view)
        progressBarHorizontal = view.findViewById(R.id.progress_bar_horizontal)
        progressBarRound = view.findViewById(R.id.progress_bar_round)
        layoutSuccess = view.findViewById(R.id.layout_success)
        layoutLoading = view.findViewById(R.id.layout_loading)
        layoutError = view.findViewById(R.id.layout_error)
        textError = view.findViewById(R.id.text_view_error)
        buttonReload = view.findViewById(R.id.button_reload)
    }

    override fun createPresenter(): IPresenter<AppState, IView> = MainPresenterImlp()

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel.isNullOrEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showSuccess()
                    if (adapter == null) {
                        adapter = MainAdapter(onListItemClickListener, dataModel)

                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showLoading()
                if (appState.progress != null) {
                    progressBarHorizontal.visibility = View.VISIBLE
                    progressBarRound.visibility = View.GONE
                    progressBarHorizontal.progress = appState.progress
                } else {
                    progressBarHorizontal.visibility = View.GONE
                    progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showError()
        textError.text = error ?: getString(R.string.undefined_error)
        buttonReload.setOnClickListener {
            presenter.getData("hi", true)
        }
    }

    private fun showSuccess() {
        layoutSuccess.makeVisible()
        layoutLoading.makeGone()
        layoutError.makeGone()
    }

    private fun showLoading() {
        layoutSuccess.makeGone()
        layoutLoading.makeVisible()
        layoutError.makeGone()
    }

    private fun showError() {
        layoutSuccess.makeGone()
        layoutLoading.makeGone()
        layoutError.makeVisible()
    }
}