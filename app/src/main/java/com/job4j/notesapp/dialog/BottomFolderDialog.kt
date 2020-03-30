package com.job4j.notesapp.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.job4j.notesapp.R

/**
 * Класс BottomFolderDialog
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class BottomFolderDialog : BottomSheetDialogFragment() {
    private lateinit var listener : BottomFolderDialogListener

    fun newInstance(position: Int) : BottomSheetDialogFragment {
        val bundle = Bundle()
        bundle.putInt("id_folder", position)

        val dialogFragment = BottomFolderDialog()
        dialogFragment.arguments = bundle
        return dialogFragment
    }

    @SuppressLint("RestrictedApi", "InflateParams")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_folder, null)

        dialog.setContentView(view)
    }

    fun setCallback(listener: BottomFolderDialogListener) {
        this.listener = listener
    }
}