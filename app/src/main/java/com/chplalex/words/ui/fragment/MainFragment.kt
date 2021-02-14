package com.chplalex.words.ui.fragment

import android.os.Bundle
import com.chplalex.words.R
import com.chplalex.words.mvp.contract.Presenter
import com.chplalex.words.mvp.contract.View
import com.chplalex.words.mvp.model.AppState

class MainFragment : BaseFragment<AppState>(R.layout.fragment_main) {

    private var adapter: MainAdapter? = null

    private val onItemClickListener = object : MainAdapter.OnItemClickListener {

    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun createPresenter(): Presenter<AppState, View> {

    }

    override fun renderData(appState: AppState) {

    }
}