package com.job4j.notesapp.dialog

import androidx.fragment.app.DialogFragment

/**
 * Интерфейс PositiveDialogListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

interface PositiveDialogListener {

    fun onClickPositive(dialog: DialogFragment, position: Int, nameFolder: String)
}