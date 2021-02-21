package com.chplalex.words.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.chplalex.words.R
import com.chplalex.words.mvp.contract.IPresenter
import com.chplalex.words.mvp.contract.IView
import com.chplalex.words.mvp.model.AppState
import com.chplalex.words.mvp.model.DataModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : BaseFragment<AppState>(R.layout.fragment_main) {

    private var adapter: MainAdapter? = null

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            Toast.makeText(context, data.word, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab = view.findViewById<FloatingActionButton>(R.id.search_fab)
        fab.setOnClickListener {
            val searchDialogFragment = SearchFragment.newInstance()
            searchDialogFragment.setOnSearchClicklistener(object : SearchClicklistener.OnSearchClickListener {

            })
        }
    }

    override fun createPresenter(): IPresenter<AppState, IView> = MainPresenterImlp()

    override fun renderData(appState: AppState) {

    }
}