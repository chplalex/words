package com.chplalex.words.ui.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chplalex.words.mvp.contract.IPresenter
import com.chplalex.words.mvp.contract.IView
import com.chplalex.words.mvp.model.AppState

abstract class BaseFragment<T: AppState>(@LayoutRes resId: Int) : Fragment(resId), IView {
    protected lateinit var presenter: IPresenter<T, IView>

    protected abstract fun createPresenter(): IPresenter<T, IView>

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