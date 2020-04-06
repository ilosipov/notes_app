package com.job4j.notesapp.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.job4j.notesapp.R
import com.job4j.notesapp.listener.BottomFolderDialogListener

/**
 * Класс BottomFolderDialog - нижний диалог для выбора действия с папкой
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class BottomFolderDialog : BottomSheetDialogFragment() {
    private lateinit var listener : BottomFolderDialogListener

    @SuppressLint("RestrictedApi", "InflateParams")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_folder, null)

        val btnDelete = view.findViewById<CardView>(R.id.btn_delete_folder)
        val btnRename = view.findViewById<CardView>(R.id.btn_rename_folder)
        val btnColor = view.findViewById<CardView>(R.id.btn_update_color_folder)

        btnDelete.setOnClickListener {
            this.listener.onClickDeleteFolder(arguments!!.getInt("position_folder"), this) }
        btnRename.setOnClickListener {
            this.listener.onClickRenameFolder(arguments!!.getInt("position_folder"), this) }
        btnColor.setOnClickListener {
            this.listener.onClickUpdateFolder(arguments!!.getInt("position_folder"), this) }

        dialog.setContentView(view)
    }

    fun setListener(listener: BottomFolderDialogListener) {
        this.listener = listener
    }
}