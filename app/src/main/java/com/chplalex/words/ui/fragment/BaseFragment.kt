package com.chplalex.words.ui.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chplalex.words.mvp.contract.Presenter
import com.chplalex.words.mvp.contract.View
import com.chplalex.words.mvp.model.AppState

abstract class BaseFragment<T: AppState>(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), View {

    protected lateinit var presenter: Presenter<T, View>

    protected abstract fun createPresenter(): Presenter<T, View>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}