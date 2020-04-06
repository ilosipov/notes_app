package com.job4j.notesapp.listener

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Интерфейс BottomNoteDialogListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 06.04.2020
 * @version $Id$
 */

interface BottomNoteDialogListener {

    fun onClickDeleteNote(position: Int, bottomDialog: BottomSheetDialogFragment)
    fun onClickShareNote(position: Int, bottomDialog: BottomSheetDialogFragment)
}