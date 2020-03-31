package com.job4j.notesapp.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.job4j.notesapp.R

/**
 * Класс RenameFolderDialog - диалоговое окно для изменения имени папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

class RenameFolderDialog : DialogFragment() {
    private lateinit var listener : PositiveDialogListener

    fun newInstance(position: Int, nameFolder: String) : RenameFolderDialog {
        val bundle = Bundle()
        bundle.putInt("id_folder", position)
        bundle.putString("name_folder", nameFolder)

        val renameFolderDialog = RenameFolderDialog()
        renameFolderDialog.arguments = bundle
        return renameFolderDialog
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_rename_folder, null)

        val btnPositive = view.findViewById<TextView>(R.id.btn_positive_rename_folder)
        val btnNegative = view.findViewById<TextView>(R.id.btn_negative_rename_folder)
        val editName = view.findViewById<TextView>(R.id.edit_rename_dialog_folder)
        editName.text = arguments?.getString("name_folder")

        btnPositive.setOnClickListener {
            listener.onClickPositive(this, arguments!!.getInt("id_folder"),
                editName.text.toString()) }
        btnNegative.setOnClickListener { this.dismiss() }

        val alertDialog = activity?.let { AlertDialog.Builder(it) }
        alertDialog?.setView(view)

        val alertDialogCreate = alertDialog?.create()
        if (alertDialogCreate?.window != null) {
            alertDialogCreate.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        return alertDialogCreate as Dialog
    }

    fun setCallback(callback: PositiveDialogListener) {
        this.listener = callback
    }
}