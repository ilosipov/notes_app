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
import com.job4j.notesapp.listener.PositiveDialogListener

/**
 * Класс DeleteNoteDialog - диалоговое окно для подтверждения удаления записи
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 06.04.2020
 * @version $Id$
 */

class DeleteNoteDialog : DialogFragment() {
    private lateinit var listener : PositiveDialogListener

    fun newInstance(position: Int) : DeleteNoteDialog {
        val bundle = Bundle()
        bundle.putInt("id_note", position)

        val deleteNoteDialog = DeleteNoteDialog()
        deleteNoteDialog.arguments = bundle
        return deleteNoteDialog
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_delete_note, null)

        val btnPositive = view.findViewById<TextView>(R.id.btn_positive_delete_note)
        btnPositive.setOnClickListener {
            listener.onClickPositive(this, arguments!!.getInt("id_note"), "", "")
        }
        val btnNegative = view.findViewById<TextView>(R.id.btn_negative_delete_note)
        btnNegative.setOnClickListener { this.dismiss() }

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