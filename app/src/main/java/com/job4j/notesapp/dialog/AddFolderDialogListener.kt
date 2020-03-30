package com.job4j.notesapp.dialog

import androidx.fragment.app.DialogFragment

/**
 * Интерфейс AddFolderDialogListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

interface AddFolderDialogListener {

    fun onClickPositive(dialog: DialogFragment, nameFolder: String)
    fun onClickNegative(dialog: DialogFragment)
}