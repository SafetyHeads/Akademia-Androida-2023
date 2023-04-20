package com.safetyheads.akademiaandroida.presentation.ui.fragments.contact_with_us

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.safetyheads.akademiaandroida.R

class ContactWithUsDialogFragment(
    private val callback: ContactWithUsCallback,
    private val dialogName: String
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage(getString(R.string.do_you_want_to_install_app, dialogName))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ -> callback.setPositiveButton() }
            .setNegativeButton(R.string.no) { _, _ -> callback.setNegativeButton() }
            .create()
    }
}