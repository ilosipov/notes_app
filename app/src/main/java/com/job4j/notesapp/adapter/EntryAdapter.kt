package com.job4j.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                   private var entrys: List<Entry>) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : EntryViewHolder {
        return EntryViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entrys[position]

        val textEntry = holder.itemView.findViewById<TextView>(R.id.text_entry)
        textEntry.text = entry.text
    }

    override fun getItemCount(): Int {
        return entrys.size
    }
}