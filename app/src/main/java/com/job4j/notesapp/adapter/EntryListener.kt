package com.job4j.notesapp.adapter

/**
 * Интерфейс EntryListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 27.03.2020
 * @version $Id$
 */

interface EntryListener {

    fun onClick(position: Int)

    fun onClickDelete(position: Int)
}