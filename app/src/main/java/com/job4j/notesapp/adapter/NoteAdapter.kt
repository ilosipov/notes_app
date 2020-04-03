package com.job4j.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R
import com.job4j.notesapp.model.Note

/**
 * Класс NoteAdapter - адаптер
 */

class NoteAdapter(private var context: Context, private var resource: Int,
                  private var notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private lateinit var listener : OnClickItemListener

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.itemView.setOnClickListener { listener.onClick(position) }

        val titleNote = holder.itemView.findViewById<TextView>(R.id.title_note)
        if (note.title.isEmpty()) {
            titleNote.visibility = View.GONE
        } else {
            titleNote.visibility = View.VISIBLE
            titleNote.text = note.title
        }

        val textNote = holder.itemView.findViewById<TextView>(R.id.text_note)
        textNote.text = note.body
    }

    override fun getItemCount() : Int {
        return notes.size
    }

    fun setCallback(listener: OnClickItemListener) {
        this.listener = listener
    }
}