package com.job4j.notesapp.store

/**
 * Класс NoteSchema - определяет схему таблицы базы данных notes
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class NoteSchema {

    class NoteTable {
        companion object {
            const val NAME = "notes"
        }

        class Cols {
            companion object {
                const val DATE = "date"
                const val TITLE = "title"
                const val BODY = "body"
                const val FOLDER_ID = "folder_id"
            }
        }
    }
}