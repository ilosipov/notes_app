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
 * Класс DeleteFolderDialog - диалоговое окно для подтверждения удаления папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

class DeleteFolderDialog : DialogFragment() {
    private lateinit var listener : PositiveDialogListener

    fun newInstance(position: Int) : DeleteFolderDialog {
        val bundle = Bundle()
        bundle.putInt("position_folder", position)

        val deleteFolderDialog = DeleteFolderDialog()
        deleteFolderDialog.arguments = bundle
        return deleteFolderDialog
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_delete_folder, null)

        val btnPositive = view.findViewById<TextView>(R.id.btn_positive_delete_folder)
        btnPositive.setOnClickListener {
            listener.onClickPositive(this, arguments!!.getInt("position_folder"), "", "") }
        val btnNegative = view.findViewById<TextView>(R.id.btn_negative_delete_folder)
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