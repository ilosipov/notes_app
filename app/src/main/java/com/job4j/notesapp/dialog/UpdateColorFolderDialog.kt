package com.job4j.notesapp.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Класс UpdateColorFolderDialog - диалоговое окно для изменения цвета папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

class UpdateColorFolderDialog : DialogFragment() {

    fun newInstance(position: Int) : UpdateColorFolderDialog {
        val bundle = Bundle()
        bundle.putInt("id_folder", position)

        val updateColorFolderDialog = UpdateColorFolderDialog()
        updateColorFolderDialog.arguments = bundle
        return updateColorFolderDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}