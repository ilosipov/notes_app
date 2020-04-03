package com.job4j.notesapp.model

/**
 * Класс Note - модель заметки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

data class Note(
    var id: Int = 0,
    var date: String = "",
    var title: String = "",
    var body: String = "",
    var folderId: Int = 0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (date != other.date) return false
        if (title != other.title) return false
        if (body != other.body) return false
        if (folderId != other.folderId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + date.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + folderId
        return result
    }

    override fun toString(): String {
        return "Note: id = $id, date = $date , title = $title, body = $body, folderId = $folderId"
    }
}