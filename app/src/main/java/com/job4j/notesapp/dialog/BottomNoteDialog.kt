package com.job4j.notesapp.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.job4j.notesapp.R
import com.job4j.notesapp.listener.BottomNoteDialogListener

/**
 * Класс BottomNoteDialog - нижний диалог для выбора действия с записью
 * @author Ilya Osipov (mailto:il.osipov.gm@gmal.com)
 * @since 06.04.2020
 * @version $Id$
 */

class BottomNoteDialog : BottomSheetDialogFragment() {
    private lateinit var listener : BottomNoteDialogListener

    @SuppressLint("InflateParams", "RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_notes, null)

        val btnDelete = view.findViewById<CardView>(R.id.btn_delete_notes)
        val btnShare = view.findViewById<CardView>(R.id.btn_share_notes)

        btnDelete.setOnClickListener { listener.onClickDeleteNote(arguments!!.getInt("id_note"), this) }
        btnShare.setOnClickListener { listener.onClickShareNote(arguments!!.getInt("id_note"), this) }

        dialog.setContentView(view)
    }

    fun setListener(listener: BottomNoteDialogListener) {
        this.listener = listener
    }
}