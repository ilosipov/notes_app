package com.job4j.notesapp.model

/**
 * Класс Entry - модель текущей записи
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

data class Entry(
    val id: Int = 0,
    val date: String = "",
    val text: String = "",
    var checked: Boolean = false) : Comparator<Entry> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entry

        if (id != other.id) return false
        if (date != other.date) return false
        if (text != other.text) return false
        if (checked != other.checked) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + date.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }

    override fun toString(): String {
        return "Entry: id = $id, date = $date, text = $text, check = $checked"
    }

    override fun compare(o1: Entry?, o2: Entry?) : Int {
        return o2?.checked?.let { o1?.checked?.compareTo(it) }!!
    }
}