package com.job4j.notesapp.fargment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.store.NoteBaseHelper

/**
 * Класс NotesFragment - представление записей в папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class NotesFragment : Fragment() {
    private val log = "NotesFragment"

    private lateinit var btnBack : ImageView
    private lateinit var titleFragment : TextView
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnNewNote : FloatingActionButton
    private lateinit var store : SQLiteDatabase

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
        Log.d(log, "onCreateView: initialization NotesFragment.")
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        store = NoteBaseHelper(context!!).writableDatabase

        titleFragment = view.findViewById(R.id.title_notes_fragment)
        titleFragment.text = arguments!!.getString("name_folder")
        btnBack = view.findViewById(R.id.btn_back_notes)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        recyclerView = view.findViewById(R.id.recycler_view_notes)

        btnNewNote = view.findViewById(R.id.btn_new_note)
        btnNewNote.setOnClickListener(this::onClickNewNote)

        return view
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickNewNote(v: View) {
        Log.d(log, "onClickNewNote: click add new note.")
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, NoteFragment()
                .newInstance(arguments!!.getInt("id_folder")))
            ?.addToBackStack(null)
            ?.commit()
    }
}