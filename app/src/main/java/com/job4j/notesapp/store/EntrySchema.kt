package com.job4j.notesapp.store

/**
 * Класс EntrySchema - определяет схему таблицы базы данных entrys
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class EntrySchema {

    class EntryTable {
        companion object {
            const val NAME : String = "entrys"
        }

        class Cols {
            companion object {
                const val DATE = "date"
                const val TEXT = "text"
                const val CHECKED = "checked"
            }
        }
    }
}