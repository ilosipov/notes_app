package com.job4j.notesapp.fargment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R

class FoldersFragment : Fragment() {
    private val log = "FoldersFragment"

    private lateinit var btnBack : ImageView
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnAddFolder : FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(log, "onCreateView: initialization FoldersFragment.")
        val view = inflater.inflate(R.layout.fragment_folders, container, false)

        btnBack = view.findViewById(R.id.btn_back_folders)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        recyclerView = view.findViewById(R.id.recycler_view_folders)
        btnAddFolder = view.findViewById(R.id.btn_add_folder)
        btnAddFolder.setOnClickListener(this::onClickAddFolder)

        return view
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAddFolder(v: View) {
        Log.d(log, "onClickAddFolder")
    }
}