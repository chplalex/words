package com.chplalex.words.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chplalex.words.R
import com.chplalex.words.contract.IInteractor
import com.chplalex.words.isOnline
import com.chplalex.words.model.data.AppState
import com.chplalex.words.ui.fragment.alert.AlertDialogFragment
import com.chplalex.words.ui.fragment.alert.AlertDialogFragment.Companion.ALERT_DIALOG_FRAGMENT_TAG
import com.chplalex.words.viewmodel.BaseViewModel

abstract class BaseFragment<T : AppState, I : IInteractor<T>>(@LayoutRes private val resId: Int) : Fragment() {

    abstract val model: BaseViewModel<T>

    protected var isNetworkAviable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(resId, container, false)

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

    abstract fun renderData(appState: T)

}