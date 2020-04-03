package com.job4j.notesapp.fargment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.job4j.notesapp.R

/**
 * Класс NoteFragment - представление для создания записи
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 02.04.2020
 * @version $Id$
 */

class NoteFragment : Fragment() {
    private val log = "NoteFragment"

    private lateinit var btnBack : ImageView
    private lateinit var editTitle : EditText
    private lateinit var editText : EditText

    fun newInstance(idFolder: Int) : Fragment {
        val bundle = Bundle()
        bundle.putInt("id_folder", idFolder)

        val noteFragment = NoteFragment()
        noteFragment.arguments = bundle
        return noteFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(log, "onCreateView: id folder = ${arguments?.getInt("id_folder")}.")
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        editTitle = view.findViewById(R.id.edit_title_note)
        editText = view.findViewById(R.id.edit_body_note)

        btnBack = view.findViewById(R.id.btn_back_note)
        btnBack.setOnClickListener { activity?.onBackPressed() }

        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(log, "onStart.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(log, "onPause.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(log, "onStop.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(log, "onDestroy.")
    }
}