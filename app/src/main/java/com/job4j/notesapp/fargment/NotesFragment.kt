package com.job4j.notesapp.fargment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.adapter.NoteAdapter
import com.job4j.notesapp.adapter.OnClickItemListener
import com.job4j.notesapp.model.Note
import com.job4j.notesapp.store.NoteBaseHelper
import com.job4j.notesapp.store.NoteSchema

/**
 * Класс NotesFragment - представление записей в папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class NotesFragment : Fragment() {
    private var notes = ArrayList<Note>()

    private lateinit var btnBack : ImageView
    private lateinit var titleFragment : TextView
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnNewNote : FloatingActionButton
    private lateinit var adapter : NoteAdapter
    private lateinit var store : SQLiteDatabase
    private lateinit var emptyText : TextView

    fun newInstance(id: Int, name: String) : Fragment {
        val bundle = Bundle()
        bundle.putInt("id_folder", id)
        bundle.putString("name_folder", name)

        val fragment = NotesFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        store = NoteBaseHelper(context!!).writableDatabase

        emptyText = view.findViewById(R.id.empty_text_notes)
        titleFragment = view.findViewById(R.id.title_notes_fragment)
        titleFragment.text = arguments!!.getString("name_folder")
        btnBack = view.findViewById(R.id.btn_back_notes)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        recyclerView = view.findViewById(R.id.recycler_view_notes)

        btnNewNote = view.findViewById(R.id.btn_new_note)
        btnNewNote.setOnClickListener(this::onClickNewNote)

        initUI()
        return view
    }

    private fun initUI() {
        notes.clear()
        val cursor = store.query(
            NoteSchema.NoteTable.NAME,
            null, "folder_id = ?",
            arrayOf("${arguments?.getInt("id_folder")}"),
            null, null, null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            notes.add(Note(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex(NoteSchema.NoteTable.Cols.DATE)),
                cursor.getString(cursor.getColumnIndex(NoteSchema.NoteTable.Cols.TITLE)),
                cursor.getString(cursor.getColumnIndex(NoteSchema.NoteTable.Cols.BODY)),
                cursor.getInt(cursor.getColumnIndex(NoteSchema.NoteTable.Cols.FOLDER_ID))
            ))
            cursor.moveToNext()
        }
        cursor.close()

        if (notes.size != 0) {
            recyclerView.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        }

        adapter = activity?.let { NoteAdapter(it, R.layout.view_note, notes) }!!
        adapter.setListener(object : OnClickItemListener {
            override fun onClick(position: Int) {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragment_container, NoteFragment().newInstance(
                        arguments!!.getInt("id_folder"),
                        notes[position].id
                    ))
                    ?.addToBackStack(null)
                    ?.commit()
            }

            override fun onLongClick(position: Int) {
                TODO("Not yet implemented")
            }
        })
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickNewNote(v: View) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, NoteFragment()
                .newInstance(arguments!!.getInt("id_folder"), 0))
            ?.addToBackStack(null)
            ?.commit()
    }
}