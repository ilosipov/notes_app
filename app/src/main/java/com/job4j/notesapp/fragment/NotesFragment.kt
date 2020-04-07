package com.job4j.notesapp.fragment

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
import androidx.core.app.ShareCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.adapter.NoteAdapter
import com.job4j.notesapp.adapter.ScrollRecyclerListener
import com.job4j.notesapp.dialog.BottomNoteDialog
import com.job4j.notesapp.dialog.DeleteNoteDialog
import com.job4j.notesapp.listener.BottomNoteDialogListener
import com.job4j.notesapp.listener.OnClickItemListener
import com.job4j.notesapp.listener.PositiveDialogListener
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
    private lateinit var emptyText : TextView
    private lateinit var titleFragment : TextView
    private lateinit var adapter : NoteAdapter
    private lateinit var store : SQLiteDatabase
    private lateinit var recyclerView : RecyclerView
    private lateinit var titleLayout : ConstraintLayout
    private lateinit var btnNewNote : FloatingActionButton

    private lateinit var animRight : Animation
    private lateinit var animBottom : Animation
    private lateinit var animCenter : Animation

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

        animRight = AnimationUtils.loadAnimation(context, R.anim.anim_right)
        animBottom = AnimationUtils.loadAnimation(context, R.anim.anim_bottom)
        animCenter = AnimationUtils.loadAnimation(context, R.anim.anim_center)

        emptyText = view.findViewById(R.id.empty_text_notes)
        titleLayout = view.findViewById(R.id.title_notes_layout)
        recyclerView = view.findViewById(R.id.recycler_view_notes)
        titleFragment = view.findViewById(R.id.title_notes_fragment)
        titleFragment.text = arguments!!.getString("name_folder")
        btnBack = view.findViewById(R.id.btn_back_notes)
        btnBack.setOnClickListener { activity!!.onBackPressed() }

        btnNewNote = view.findViewById(R.id.btn_new_note)
        btnNewNote.setOnClickListener { onClickNote(NoteFragment()
            .newInstance(arguments!!.getInt("id_folder"), 0)) }

        emptyText.startAnimation(animCenter)
        titleLayout.startAnimation(animRight)
        btnNewNote.startAnimation(animBottom)
        recyclerView.startAnimation(animBottom)

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
        adapter.setListener(object :
            OnClickItemListener {
            override fun onClick(position: Int) {
                onClickNote(NoteFragment().newInstance(
                    arguments!!.getInt("id_folder"), notes[position].id))
            }

            override fun onLongClick(position: Int) {
                onLongClickNote(position)
            }
        })
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addOnScrollListener(ScrollRecyclerListener(btnNewNote))
        recyclerView.adapter = adapter
    }

    private fun onClickNote(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun onLongClickNote(position: Int) {
        val scrollPosition = recyclerView.layoutManager?.onSaveInstanceState()
        val fm = activity?.supportFragmentManager
        val bottomBundle = Bundle()
        bottomBundle.putInt("id_note", position)

        val bottomNoteDialog = BottomNoteDialog()
        bottomNoteDialog.arguments = bottomBundle
        bottomNoteDialog.setListener(object : BottomNoteDialogListener {
            override fun onClickDeleteNote(position: Int, bottomDialog: BottomSheetDialogFragment) {
                val dialogDeleteNote = DeleteNoteDialog().newInstance(position)
                dialogDeleteNote.setListener(object : PositiveDialogListener {
                    override fun onClickPositive(dialog: DialogFragment, position: Int,
                                                 nameFolder: String, colorFolder: String) {
                        store.delete(NoteSchema.NoteTable.NAME, "_id = ?",
                            arrayOf( "${notes[position].id}" ))
                        notes.remove(notes[position])
                        adapter.notifyItemRemoved(position)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager?.onRestoreInstanceState(scrollPosition)
                        dialog.dismiss()
                    }
                })
                fm?.let { dialogDeleteNote.show(it, "delete_note_dialog") }
                bottomNoteDialog.dismiss()
            }

            override fun onClickShareNote(position: Int, bottomDialog: BottomSheetDialogFragment) {
                val shareIntent = ShareCompat.IntentBuilder
                    .from(activity!!)
                    .setType("text/plain").intent
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, notes[position].title + "\n\n" +
                            notes[position].body)
                startActivity(Intent.createChooser(shareIntent, "send note"))
                bottomNoteDialog.dismiss()
            }
        })
        fm?.let { bottomNoteDialog.show(it, "bottom_note_dialog") }
    }
}