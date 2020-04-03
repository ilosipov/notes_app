package com.job4j.notesapp.store

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Класс NoteBaseHelper - создает базу данных заметок
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class NoteBaseHelper(context: Context) : SQLiteOpenHelper(context, name_database, null, version_database) {

    companion object {
        private const val name_database = "notes.db"
        private const val version_database = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table ${NoteSchema.NoteTable.NAME}(" +
                "_id integer primary key autoincrement," +
                "${NoteSchema.NoteTable.Cols.DATE}," +
                "${NoteSchema.NoteTable.Cols.TITLE}," +
                "${NoteSchema.NoteTable.Cols.BODY}," +
                "${NoteSchema.NoteTable.Cols.FOLDER_ID} " +
                "integer references ${FolderSchema.FolderTable.NAME})")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}