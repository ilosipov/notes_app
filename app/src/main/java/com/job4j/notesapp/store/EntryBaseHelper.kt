package com.job4j.notesapp.store

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Класс EntryBaseHelper - создает базу данных текущих записей
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class EntryBaseHelper(context: Context) : SQLiteOpenHelper(context, name_database, null, version_database) {

    companion object {
        private const val name_database = "entrys.db"
        private const val version_database = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table ${EntrySchema.EntryTable.NAME}(" +
                "_id integer primary key autoincrement," +
                "${EntrySchema.EntryTable.Cols.DATE}," +
                "${EntrySchema.EntryTable.Cols.TEXT}," +
                "${EntrySchema.EntryTable.Cols.CHECKED})")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}