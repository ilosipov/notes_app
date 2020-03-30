package com.job4j.notesapp.fargment

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.adapter.FolderAdapter
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
    private lateinit var adapter : FolderAdapter

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

        initUI()
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
                cursor.getString(cursor.getColumnIndex("name_folder")),
                cursor.getString(cursor.getColumnIndex("color_folder"))
            ))
            cursor.moveToNext()
        }
        cursor.close()

        adapter = activity?.let { FolderAdapter(it, R.layout.view_folder, folders) }!!
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAddFolder(v: View) {
        Log.d(log, "onClickAddFolder")
        val fragmentManager = activity?.supportFragmentManager
        val dialogAddFolder = AddFolderDialog()

        dialogAddFolder.setCallback(object : AddFolderDialogListener {
            override fun onClickPositive(dialog: DialogFragment, nameFolder: String) {
                val contentValues = ContentValues()
                contentValues.put(FolderSchema.FolderTable.Cols.NAME_FOLDER,
                    if (nameFolder.isNotEmpty()) nameFolder else getString(R.string.default_name_folder))
                contentValues.put(FolderSchema.FolderTable.Cols.COLOR_FOLDER, getColorFolder())
                store.insert(FolderSchema.FolderTable.NAME, null, contentValues)

                initUI()
                dialogAddFolder.dismiss()
            }

            override fun onClickNegative(dialog: DialogFragment) {
                dialogAddFolder.dismiss()
            }
        })
        fragmentManager?.let { dialogAddFolder.show(it, "dialog_add_folder") }
    }

    private fun getColorFolder() : String {
        val colors = arrayListOf(
            "#0000FF", "#FF0000", "#FFA500", "#00FF00", "#FFFF00", "#C8A2C8", "#00BFFF", "#660099",
            "#008080", "#CD853F", "#CC8899", "#3A75C4", "#FDE910", "#F3C3F3", "#C0C0C0", "#ACE1AF"
        )
        return colors[(Math.random()*colors.size - 1).toInt()]
    }
}