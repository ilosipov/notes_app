package com.job4j.notesapp.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R
import com.job4j.notesapp.model.Entry

/**
 * Класс EntryAdapter - адатпер текущих записей
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class EntryAdapter(private var context: Context, private var resource: Int,
                   private var entrys: List<Entry>) :
    RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {
    private lateinit var listener : EntryListener

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : EntryViewHolder {
        return EntryViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entrys[position]
        val itemView = holder.itemView

        val btnDelete = holder.itemView.findViewById<ImageView>(R.id.btn_delete_entry)
        val textEntry = holder.itemView.findViewById<TextView>(R.id.text_entry)
        if (!entry.checked) {
            textEntry.text = entry.text
            btnDelete.visibility = View.GONE
        } else {
            textEntry.setTextColor(Color.parseColor("#61000000"))
            textEntry.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            textEntry.text = entry.text
            btnDelete.visibility = View.VISIBLE
        }
        itemView.setOnClickListener { listener.onClick(position) }
        btnDelete.setOnClickListener { listener.onClickDelete(position) }
    }

    override fun getItemCount(): Int {
        return entrys.size
    }

    fun setListener(listener: EntryListener) {
        this.listener = listener
    }
}