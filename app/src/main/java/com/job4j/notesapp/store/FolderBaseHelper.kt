package com.job4j.notesapp.store

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Класс FolderBaseHelper - создает базу данных папок для записей
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class FolderBaseHelper(context: Context) : SQLiteOpenHelper(context, name_database, null, version_database) {

    companion object {
        private const val name_database = "folders.db"
        private const val version_database = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table ${FolderSchema.FolderTable.NAME}(" +
                "_id integer primary key autoincrement," +
                "${FolderSchema.FolderTable.Cols.NAME_FOLDER}," +
                "${FolderSchema.FolderTable.Cols.COLOR_FOLDER})")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}