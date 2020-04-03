package com.job4j.notesapp.adapter

/**
 * Интерфейс FolderListener
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

interface OnClickItemListener {

    fun onClick(position: Int)
    fun onLongClick(position: Int)
}