package com.chplalex.base

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.utils.network.OnlineLiveData
import com.chplalex.utils.ui.AlertDialogFragment
import com.chplalex.utils.ui.AlertDialogFragment.Companion.ALERT_DIALOG_FRAGMENT_TAG
import com.chplalex.utils.ui.makeGone
import com.chplalex.utils.ui.makeVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment<T : AppState, I : IInteractor<T>>(@LayoutRes private val resId: Int) : Fragment() {

    abstract val model: BaseViewModel<T>

    abstract val titleRes: Int
    abstract val layoutWorkingRes: Int

    private val onlineLiveData: OnlineLiveData by inject { parametersOf(requireContext()) }

    protected var isNetworkAvailable: Boolean = false

    protected lateinit var indicatorLinear: LinearProgressIndicator
    protected lateinit var indicatorCircular: CircularProgressIndicator
    protected lateinit var layoutWorking: FrameLayout
    protected lateinit var layoutLoading: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
        initViewModel()
        activity?.title = getString(titleRes)
    }

    override fun onResume() {
        super.onResume()
        showNetworkStatus()
    }

    private fun showNetworkStatus() {
        if (isNetworkAvailable) {
            Toast.makeText(context, R.string.dialog_message_device_is_online, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, R.string.dialog_message_device_is_offline, Toast.LENGTH_LONG).show()
        }
    }

    private fun subscribeToNetworkChange() {
        onlineLiveData.observe(
            this,
            {
                isNetworkAvailable = it
                showNetworkStatus()
            })
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