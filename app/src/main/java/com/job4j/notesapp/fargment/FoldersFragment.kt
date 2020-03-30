package com.job4j.notesapp.fargment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.dialog.AddFolderDialog
import com.job4j.notesapp.dialog.AddFolderDialogListener
import com.job4j.notesapp.model.Folder
import com.job4j.notesapp.store.FolderBaseHelper
import com.job4j.notesapp.store.FolderSchema

class FoldersFragment : Fragment() {
    private val log = "FoldersFragment"
    private var folders = ArrayList<Folder>()

    private lateinit var btnBack : ImageView
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnAddFolder : FloatingActionButton
    private lateinit var store : SQLiteDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(log, "onCreateView: initialization FoldersFragment.")
        val view = inflater.inflate(R.layout.fragment_folders, container, false)
        store = FolderBaseHelper(context!!).writableDatabase

        btnBack = view.findViewById(R.id.btn_back_folders)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        recyclerView = view.findViewById(R.id.recycler_view_folders)
        btnAddFolder = view.findViewById(R.id.btn_add_folder)
        btnAddFolder.setOnClickListener(this::onClickAddFolder)

        return view
    }

    private fun initUI() {
        folders.clear()
        val cursor = store.query(
            FolderSchema.FolderTable.NAME,
            null, null, null,
            null, null, null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            folders.add(Folder(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("name_folder"))
            ))
            cursor.moveToNext()
        }
        cursor.close()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAddFolder(v: View) {
        Log.d(log, "onClickAddFolder")
        val fragmentManager = activity?.supportFragmentManager
        val dialogAddFolder = AddFolderDialog()

        dialogAddFolder.setCallback(object : AddFolderDialogListener {
            override fun onClickPositive(dialog: DialogFragment, nameFolder: String) {
                dialogAddFolder.dismiss()
            }

            override fun onClickNegative(dialog: DialogFragment) {
                dialogAddFolder.dismiss()
            }
        })
        fragmentManager?.let { dialogAddFolder.show(it, "dialog_add_folder") }
    }
}