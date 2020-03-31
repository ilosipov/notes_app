package com.job4j.notesapp.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Класс DeleteFolderDialog - диалоговое окно для подтверждения удаления папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

class DeleteFolderDialog : DialogFragment() {

    fun newInstance(position: Int) : DeleteFolderDialog {
        val bundle = Bundle()
        bundle.putInt("id_folder", position)

        val deleteFolderDialog = DeleteFolderDialog()
        deleteFolderDialog.arguments = bundle
        return deleteFolderDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}