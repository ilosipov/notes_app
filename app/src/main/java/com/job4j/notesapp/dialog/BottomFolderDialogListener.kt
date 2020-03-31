package com.job4j.notesapp.dialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Интерфейс BottomFolderDialogListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

interface BottomFolderDialogListener {

    fun onClickDeleteFolder(position: Int, bottomDialog: BottomSheetDialogFragment)
    fun onClickRenameFolder(position: Int, bottomDialog: BottomSheetDialogFragment)
    fun onClickUpdateFolder(position: Int, bottomDialog: BottomSheetDialogFragment)
}