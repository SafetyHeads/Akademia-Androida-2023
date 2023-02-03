package com.safetyheads.akademiaandroida.contact_with_us

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.safetyheads.akademiaandroida.R

class ContactWithUsDialogFragment(
    private val callback: ContactWithUsCallback,
    private val dialogName: String
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage(getString(R.string.would_you_like_to_install) + " $dialogName " + getString(R.string.app))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _: DialogInterface?, _: Int -> callback.setPositiveButton() }
            .setNegativeButton(R.string.no) { _: DialogInterface?, _: Int -> callback.setNegativeButton() }
            .create()
    }
}