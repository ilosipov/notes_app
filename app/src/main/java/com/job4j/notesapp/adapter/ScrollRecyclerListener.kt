package com.job4j.notesapp.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Класс ScrollRecyclerListener - слушатель помогающий убрать FloatingActionButton
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 06.04.2020
 * @version $Id$
 */

class ScrollRecyclerListener(floatingActionButton: FloatingActionButton) :
    RecyclerView.OnScrollListener() {
    private val floatBtn = floatingActionButton

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0 || dy < 0 && floatBtn.isShown) floatBtn.hide()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == SCROLL_STATE_IDLE) floatBtn.show()
        super.onScrollStateChanged(recyclerView, newState)
    }
}