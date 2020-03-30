package com.job4j.notesapp.dialog

/**
 * Интерфейс BottomFolderDialogListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

interface BottomFolderDialogListener {

    fun onClickDeleteFolder(position: Int, bottomDialog: BottomFolderDialog)
    fun onClickRenameFolder(position: Int, bottomDialog: BottomFolderDialog)
    fun onClickUpdateFolder(position: Int, bottomDialog: BottomFolderDialog)
}