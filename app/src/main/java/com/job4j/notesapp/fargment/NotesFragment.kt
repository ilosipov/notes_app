package com.job4j.notesapp.fargment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R

/**
 * Класс NotesFragment - представление записей в папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class NotesFragment : Fragment() {
    private val log = "NotesFragment"

    private lateinit var btnBack : ImageView
    private lateinit var recyclerView : RecyclerView
    private lateinit var titleFragment : TextView

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
        Log.d(log, "onCreateView: initialization NotesFragment: " +
                "id folder = ${arguments!!.get("id_folder")}")
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        titleFragment = view.findViewById(R.id.title_notes_fragment)
        titleFragment.text = arguments!!.getString("name_folder")
        btnBack = view.findViewById(R.id.btn_back_notes)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        recyclerView = view.findViewById(R.id.recycler_view_notes)

        return view
    }
}