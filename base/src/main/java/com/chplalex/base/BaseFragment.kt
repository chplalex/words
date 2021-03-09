package com.chplalex.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.utils.network.isOnline
import com.chplalex.utils.ui.AlertDialogFragment
import com.chplalex.utils.ui.AlertDialogFragment.Companion.ALERT_DIALOG_FRAGMENT_TAG
import com.chplalex.utils.ui.makeGone
import com.chplalex.utils.ui.makeVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator

abstract class BaseFragment<T : AppState, I : IInteractor<T>>(@LayoutRes private val resId: Int) : Fragment() {

    abstract val model: BaseViewModel<T>

    abstract val titleRes: Int
    abstract val layoutWorkingRes: Int

    protected var isNetworkAvailable: Boolean = false

    protected lateinit var indicatorLinear: LinearProgressIndicator
    protected lateinit var indicatorCircular: CircularProgressIndicator
    protected lateinit var layoutWorking: FrameLayout
    protected lateinit var layoutLoading: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        activity?.title = getString(titleRes)
    }

    abstract fun initViewModel()

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
        layoutWorking = view.findViewById(layoutWorkingRes)
        layoutLoading = view.findViewById(R.id.layout_loading)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(requireContext())
        if (!isNetworkAvailable && isAlertDialogNull()) {
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
                    indicatorLinear.progress = appState.progress!!
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