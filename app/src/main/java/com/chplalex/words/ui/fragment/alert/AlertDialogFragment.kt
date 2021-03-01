package com.chplalex.words.ui.fragment.alert

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.chplalex.words.ui.getAlertDialog
import com.chplalex.words.ui.getStubAlertDialog

class AlertDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            return getAlertDialog(requireContext(), it.getString(TITLE_EXTRA), it.getString(MESSAGE_EXTRA))
        } ?:
            return getStubAlertDialog(requireContext())
    }

    companion object {

        const val ALERT_DIALOG_FRAGMENT_TAG = "0c38bcdc-587f-41a6-8ea2-15def4929ffc"
        private const val TITLE_EXTRA = "89cbce59-e28f-418f-b470-ff67125c2e2f"
        private const val MESSAGE_EXTRA = "0dd00b66-91c2-447d-b627-530065040905"

        fun newInstance(title: String?, message: String?) = AlertDialogFragment().also {
            it.arguments = Bundle().apply {
                putString(TITLE_EXTRA, title)
                putString(MESSAGE_EXTRA, message)
            }
        }
    }
}
