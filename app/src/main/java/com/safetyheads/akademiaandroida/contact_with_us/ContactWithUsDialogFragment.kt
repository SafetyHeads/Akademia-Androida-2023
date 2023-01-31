package com.safetyheads.akademiaandroida.contact_with_us

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ContactWithUsDialogFragment(
    private val callback: ContactWithUsCallback,
    private val dialogName: String
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage("Czy chcesz zainstalować aplikację $dialogName?")
            .setCancelable(false)
            .setPositiveButton("Tak") { _: DialogInterface?, _: Int -> callback.setPositiveButton() }
            .setNegativeButton("Nie") { _: DialogInterface?, _: Int -> callback.setNegativeButton() }
            .create()
    }
}