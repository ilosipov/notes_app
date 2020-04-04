package com.job4j.notesapp.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.job4j.notesapp.R

/**
 * Класс AddFolderDialog - диалоговое окно для создания папки записей
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class AddFolderDialog : DialogFragment() {
    private lateinit var listener : PositiveDialogListener

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_new_folder, null)

        val editName = view.findViewById<EditText>(R.id.edit_name_dialog_folder)
        val btnPositive = view.findViewById<TextView>(R.id.btn_dialog_folder_positive)
        val btnNegative = view.findViewById<TextView>(R.id.btn_dialog_folder_negative)

        btnPositive.setOnClickListener { listener.onClickPositive(this, 0,
            editName.text.toString().trim(), "") }
        btnNegative.setOnClickListener { this.dismiss()}

        val alertDialog = activity?.let { AlertDialog.Builder(it) }
        alertDialog?.setView(view)

        val alertDialogCreate = alertDialog?.create()
        if (alertDialogCreate?.window != null) {
            alertDialogCreate.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        return alertDialogCreate as Dialog
    }

    fun setListener(listener: PositiveDialogListener) {
        this.listener = listener
    }
}