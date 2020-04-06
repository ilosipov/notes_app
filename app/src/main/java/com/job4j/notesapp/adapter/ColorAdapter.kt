package com.job4j.notesapp.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R
import com.job4j.notesapp.listener.OnClickItemListener

/**
 * Класс ColorAdapter - адаптер цветов для папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class ColorAdapter(private var context: Context, private var resource: Int, private var colors: List<String>) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    private lateinit var listener : OnClickItemListener

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.itemView.setOnClickListener { listener.onClick(position) }

        val imageColor = holder.itemView.findViewById<ImageView>(R.id.image_color)
        imageColor.setImageDrawable(ColorDrawable(Color.parseColor(color)))
    }

    override fun getItemCount() : Int {
        return colors.size
    }

    fun setListener(listener: OnClickItemListener) {
        this.listener = listener
    }
}