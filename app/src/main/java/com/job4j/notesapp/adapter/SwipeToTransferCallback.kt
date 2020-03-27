package com.job4j.notesapp.adapter

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R

/**
 * Класс SwipeToTransferCallback
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 27.03.2020
 * @version $Id$
 */

abstract class SwipeToTransferCallback(context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    private val transferIcon = ContextCompat.getDrawable(context, R.drawable.ic_redo)
    private val intrinsicWidth = transferIcon!!.intrinsicWidth
    private val intrinsicHeight = transferIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#919191")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) : Int {
        if (viewHolder.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.left + dX, itemView.top.toFloat(),
                itemView.left.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        background.color = backgroundColor
        background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
        background.draw(c)

        val transferIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val transferIconMargin = (itemHeight - intrinsicHeight) / 2
        val transferIconLeft = itemView.left + transferIconMargin
        val transferIconRight = itemView.left + transferIconMargin + intrinsicWidth
        val transferIconBottom = transferIconTop + intrinsicHeight

        transferIcon?.setBounds(transferIconLeft, transferIconTop, transferIconRight, transferIconBottom)
        transferIcon?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }
}