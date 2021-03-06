package com.job4j.notesapp.fragment

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.activity.NotesActivity
import com.job4j.notesapp.adapter.FolderAdapter
import com.job4j.notesapp.adapter.ScrollRecyclerListener
import com.job4j.notesapp.listener.OnClickItemListener
import com.job4j.notesapp.dialog.*
import com.job4j.notesapp.listener.BottomFolderDialogListener
import com.job4j.notesapp.listener.PositiveDialogListener
import com.job4j.notesapp.model.Folder
import com.job4j.notesapp.store.FolderBaseHelper
import com.job4j.notesapp.store.FolderSchema
import com.job4j.notesapp.store.NoteBaseHelper
import com.job4j.notesapp.store.NoteSchema

/**
 * Класс FoldersFragment - представление списка папок с записями
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class FoldersFragment : Fragment() {
    private var folders = ArrayList<Folder>()

    private lateinit var btnBack : ImageView
    private lateinit var emptyText : TextView
    private lateinit var store : SQLiteDatabase
    private lateinit var adapter : FolderAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var titleLayout : ConstraintLayout
    private lateinit var btnAddFolder : FloatingActionButton

    private lateinit var animRight : Animation
    private lateinit var animBottom : Animation
    private lateinit var animCenter : Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_folders, container, false)
        store = FolderBaseHelper(context!!).writableDatabase

        animRight = AnimationUtils.loadAnimation(context, R.anim.anim_right)
        animBottom = AnimationUtils.loadAnimation(context, R.anim.anim_bottom)
        animCenter = AnimationUtils.loadAnimation(context, R.anim.anim_center)

        titleLayout = view.findViewById(R.id.title_folders_layout)
        emptyText = view.findViewById(R.id.empty_text_folder)
        btnBack = view.findViewById(R.id.btn_back_folders)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        recyclerView = view.findViewById(R.id.recycler_view_folders)
        btnAddFolder = view.findViewById(R.id.btn_add_folder)
        btnAddFolder.setOnClickListener(this::onClickAddFolder)

        emptyText.startAnimation(animCenter)
        titleLayout.startAnimation(animRight)
        recyclerView.startAnimation(animBottom)
        btnAddFolder.startAnimation(animBottom)

        initUI()
        return view
    }

    private fun initUI() {
        folders.clear()
        val cursor = store.query(FolderSchema.FolderTable.NAME,
            null, null, null,
            null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            folders.add(Folder(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex(FolderSchema.FolderTable.Cols.NAME_FOLDER)),
                cursor.getString(cursor.getColumnIndex(FolderSchema.FolderTable.Cols.COLOR_FOLDER))
            ))
            cursor.moveToNext()
        }
        cursor.close()

        if (folders.size != 0) {
            recyclerView.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        }

        adapter = activity?.let { FolderAdapter(it, R.layout.view_folder, folders) }!!
        adapter.setListener(object :
            OnClickItemListener {
            override fun onClick(position: Int) {
                startActivity(Intent(context, NotesActivity::class.java)
                    .putExtra("id_folder", folders[position].id)
                    .putExtra("name_folder", folders[position].name))
            }
            override fun onLongClick(position: Int) { onLongClickFolder(position) }
        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addOnScrollListener(ScrollRecyclerListener(btnAddFolder))
        recyclerView.adapter = adapter
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAddFolder(v: View) {
        val fragmentManager = activity?.supportFragmentManager
        val dialogAddFolder = AddFolderDialog()

        dialogAddFolder.setListener(object :
            PositiveDialogListener {
            override fun onClickPositive(dialog: DialogFragment, position: Int, nameFolder: String,
                                         colorFolder: String) {
                val contentValues = ContentValues()
                contentValues.put(FolderSchema.FolderTable.Cols.NAME_FOLDER,
                    if (nameFolder.isNotEmpty()) nameFolder else getString(R.string.default_name_folder))
                contentValues.put(FolderSchema.FolderTable.Cols.COLOR_FOLDER, getColorFolder())
                store.insert(FolderSchema.FolderTable.NAME, null, contentValues)

                initUI()
                dialogAddFolder.dismiss()
            }
        })
        fragmentManager?.let { dialogAddFolder.show(it, "dialog_add_folder") }
    }

    private fun onLongClickFolder(position: Int) {
        val scrollPosition = recyclerView.layoutManager?.onSaveInstanceState()
        val fm = activity?.supportFragmentManager
        val bundle = Bundle()
        bundle.putInt("position_folder", position)
        bundle.putString("name_folder", folders[position].name)

        val bottomFolderDialog = BottomFolderDialog()
        bottomFolderDialog.arguments = bundle
        bottomFolderDialog.setListener(object : BottomFolderDialogListener {
            override fun onClickDeleteFolder(position: Int, bottomDialog: BottomSheetDialogFragment) {
                val dialogDeleteFolder = DeleteFolderDialog().newInstance(position)
                dialogDeleteFolder.setListener(object : PositiveDialogListener {
                    override fun onClickPositive(dialog: DialogFragment, position: Int,
                                                 nameFolder: String, colorFolder: String) {
                        val storeNotes = NoteBaseHelper(context!!).writableDatabase
                        store.delete(FolderSchema.FolderTable.NAME, "_id = ?",
                            arrayOf( "${folders[position].id}" ))
                        storeNotes.delete(NoteSchema.NoteTable.NAME, "folder_id = ?",
                            arrayOf( "${folders[position].id}" ))

                        folders.remove(folders[position])
                        adapter.notifyItemRemoved(position)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager?.onRestoreInstanceState(scrollPosition)
                        dialog.dismiss()
                    }
                })
                fm?.let { dialogDeleteFolder.show(it, "delete_folder_dialog") }
                bottomFolderDialog.dismiss()
            }

            override fun onClickRenameFolder(position: Int, bottomDialog: BottomSheetDialogFragment) {
                val dialogRenameFolder = RenameFolderDialog().newInstance(position, folders[position].name)
                dialogRenameFolder.setListener(object : PositiveDialogListener {
                    override fun onClickPositive(dialog: DialogFragment, position: Int,
                                                 nameFolder: String, colorFolder: String) {
                        val contentValues = ContentValues()
                        contentValues.put(FolderSchema.FolderTable.Cols.NAME_FOLDER, nameFolder)
                        store.update(FolderSchema.FolderTable.NAME, contentValues, "_id = ?",
                            arrayOf( "${folders[position].id}" ))
                        folders[position].name = nameFolder
                        adapter.notifyItemChanged(position)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager?.onRestoreInstanceState(scrollPosition)
                        dialog.dismiss()
                    }
                })
                fm?.let { dialogRenameFolder.show(it, "rename_folder_dialog") }
                bottomFolderDialog.dismiss()
            }

            override fun onClickUpdateFolder(position: Int, bottomDialog: BottomSheetDialogFragment) {
                val dialogUpdateColorFolder = UpdateColorFolderDialog().newInstance(position)
                dialogUpdateColorFolder.setListener(object : PositiveDialogListener {
                    override fun onClickPositive(dialog: DialogFragment, position: Int,
                                                 nameFolder: String, colorFolder: String) {
                        val contentValues = ContentValues()
                        contentValues.put(FolderSchema.FolderTable.Cols.COLOR_FOLDER, colorFolder)
                        store.update(FolderSchema.FolderTable.NAME, contentValues, "_id = ?",
                            arrayOf( "${folders[position].id}" ))
                        folders[position].colorFolder = colorFolder
                        adapter.notifyItemChanged(position)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager?.onRestoreInstanceState(scrollPosition)
                        dialog.dismiss()
                    }
                })
                fm?.let { dialogUpdateColorFolder.show(it, "update_folder_dialog") }
                bottomFolderDialog.dismiss()
            }
        })
        fm?.let { bottomFolderDialog.show(it, "bottom_folder_dialog") }
    }

    private fun getColorFolder() : String {
        val colors = arrayListOf(
            "#0000FF", "#FF0000", "#FFA500", "#00FF00", "#FFFF00", "#C8A2C8", "#00BFFF", "#660099",
            "#008080", "#CD853F", "#CC8899", "#3A75C4", "#FDE910", "#F3C3F3", "#C0C0C0", "#ACE1AF"
        )
        return colors[(Math.random()*colors.size - 1).toInt()]
    }
}