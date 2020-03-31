package com.job4j.notesapp.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Класс RenameFolderDialog - диалоговое окно для изменения имени папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

class RenameFolderDialog : DialogFragment() {

    fun newInstance(position: Int) : RenameFolderDialog {
        val bundle = Bundle()
        bundle.putInt("id_folder", position)

        val renameFolderDialog = RenameFolderDialog()
        renameFolderDialog.arguments = bundle
        return renameFolderDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}