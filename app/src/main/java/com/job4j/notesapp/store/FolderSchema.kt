package com.job4j.notesapp.store

/**
 * Класс FolderSchema - определяет схему таблицы базы данных folders
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class FolderSchema {

    class FolderTable {
        companion object {
            const val NAME : String = "folders"
        }

        class Cols {
            companion object {
                const val NAME_FOLDER = "name_folder"
                const val COLOR_FOLDER = "color_folder"
            }
        }
    }
}