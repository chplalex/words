package com.chplalex.words.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chplalex.words.R
import com.chplalex.words.contract.IInteractor
import com.chplalex.words.isOnline
import com.chplalex.words.makeGone
import com.chplalex.words.makeVisible
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.ui.fragment.alert.AlertDialogFragment
import com.chplalex.words.ui.fragment.alert.AlertDialogFragment.Companion.ALERT_DIALOG_FRAGMENT_TAG
import com.chplalex.words.viewmodel.BaseViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator

abstract class BaseFragment<T : AppState, I : IInteractor<T>>(@LayoutRes private val resId: Int) : Fragment() {

    abstract val model: BaseViewModel<T>

    protected var isNetworkAviable: Boolean = false

    protected lateinit var indicatorLinear: LinearProgressIndicator
    protected lateinit var indicatorCircular: CircularProgressIndicator
    protected lateinit var layoutWorking: FrameLayout
    protected lateinit var layoutLoading: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(resId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    protected open fun initViews(view: View) {
        indicatorLinear = view.findViewById(R.id.indicator_linear)
        indicatorCircular = view.findViewById(R.id.indicator_circular)
        layoutWorking = view.findViewById(R.id.fragment_main_layout_working)
        layoutLoading = view.findViewById(R.id.layout_loading)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAviable = isOnline(requireContext())
        if (!isNetworkAviable && isAlertDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(parentFragmentManager, ALERT_DIALOG_FRAGMENT_TAG)
    }

    private fun isAlertDialogNull() = parentFragmentManager.findFragmentByTag(ALERT_DIALOG_FRAGMENT_TAG) == null

    protected fun renderData(appState: T) {
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
                    setDataToAdapter(dataModel)
                }
            }
            is AppState.Loading -> {
                showLoading()
                if (appState.progress == null) {
                    indicatorCircular.makeVisible()
                    indicatorLinear.makeGone()
                } else {
                    indicatorCircular.makeGone()
                    indicatorLinear.makeVisible()
                    indicatorLinear.progress = appState.progress
                }
            }
            is AppState.Error -> {
                showWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    abstract fun setDataToAdapter(data: List<DataModel>)

    private fun showWorking() {
        layoutWorking.makeVisible()
        layoutLoading.makeGone()
    }

    private fun showLoading() {
        layoutWorking.makeGone()
        layoutLoading.makeVisible()
    }

}