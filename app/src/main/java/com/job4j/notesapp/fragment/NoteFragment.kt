package com.job4j.notesapp.fragment

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ShareCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.job4j.notesapp.R
import com.job4j.notesapp.dialog.DeleteNoteDialog
import com.job4j.notesapp.listener.PositiveDialogListener
import com.job4j.notesapp.store.NoteBaseHelper
import com.job4j.notesapp.store.NoteSchema
import java.text.SimpleDateFormat
import java.util.*

/**
 * Класс NoteFragment - представление для создания записи
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 02.04.2020
 * @version $Id$
 */

class NoteFragment : Fragment() {
    private lateinit var btnBack : ImageView
    private lateinit var btnShare : ImageView
    private lateinit var btnDelete : ImageView
    private lateinit var editText : EditText
    private lateinit var editTitle : EditText
    private lateinit var store : SQLiteDatabase
    private lateinit var imm : InputMethodManager
    private lateinit var titleLayout : ConstraintLayout

    private lateinit var animRight : Animation

    fun newInstance(idFolder: Int, idNote: Int) : Fragment {
        val bundle = Bundle()
        bundle.putInt("id_folder", idFolder)
        bundle.putInt("id_note", idNote)

        val noteFragment = NoteFragment()
        noteFragment.arguments = bundle
        return noteFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        store = NoteBaseHelper(context!!).writableDatabase

        animRight = AnimationUtils.loadAnimation(context, R.anim.anim_right)

        editTitle = view.findViewById(R.id.edit_title_note)
        editTitle.imeOptions = EditorInfo.IME_ACTION_NEXT
        editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editTitle.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

        editText = view.findViewById(R.id.edit_body_note)
        titleLayout = view.findViewById(R.id.title_note_layout)

        btnBack = view.findViewById(R.id.btn_back_note)
        btnBack.setOnClickListener {
            if (arguments?.getInt("id_note") == 0) {
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
            }
            activity?.onBackPressed()
        }

        btnShare = view.findViewById(R.id.btn_share_note)
        btnDelete = view.findViewById(R.id.btn_delete_note)
        if (arguments?.getInt("id_note") == 0) {
            btnDelete.visibility = View.GONE
            btnShare.visibility = View.GONE
        } else {
            btnDelete.visibility = View.VISIBLE
            btnShare.visibility = View.VISIBLE
            btnDelete.setOnClickListener(this::onClickDelete)
            btnShare.setOnClickListener(this::onClickShare)
        }

        editText.startAnimation(animRight)
        editTitle.startAnimation(animRight)
        titleLayout.startAnimation(animRight)

        initUI()
        return view
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickDelete(v: View) {
        val dialogDeleteNote = DeleteNoteDialog().newInstance(arguments!!.getInt("id_note"))
        dialogDeleteNote.setListener(object : PositiveDialogListener {
            override fun onClickPositive(dialog: DialogFragment, position: Int,
                                         nameFolder: String, colorFolder: String) {
                store.delete(NoteSchema.NoteTable.NAME, "_id = ?", arrayOf( "$position" ))
                activity?.onBackPressed()
                dialog.dismiss()
            }
        })
        activity?.supportFragmentManager?.let { dialogDeleteNote.show(it, "delete_note_dialog") }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickShare(v: View) {
        val shareIntent = ShareCompat.IntentBuilder
            .from(activity!!)
            .setType("text/plain").intent
            .setAction(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, "${editTitle.text}\n\n" +
                    "${editText.text}")
        startActivity(Intent.createChooser(shareIntent, "send note"))
    }

    private fun initUI() {
        if (arguments?.getInt("id_note") != 0) {
            val cursor = store.query(NoteSchema.NoteTable.NAME,
                null, "_id = ?",
                arrayOf("${arguments!!.getInt("id_note")}"),
                null, null, null
            )
            cursor.moveToFirst()
            editTitle.setText(cursor.getString(cursor.getColumnIndex("title")))
            editText.setText(cursor.getString(cursor.getColumnIndex("body")))
            cursor.close()

        } else {
            editText.requestFocus()
            imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val contentValues = ContentValues()
        contentValues.put(NoteSchema.NoteTable.Cols.DATE, getDateFormat())
        contentValues.put(NoteSchema.NoteTable.Cols.TITLE, editTitle.text.toString().trim())
        contentValues.put(NoteSchema.NoteTable.Cols.BODY, editText.text.toString().trim())

        if (arguments?.getInt("id_note") != 0) {
            store.update(NoteSchema.NoteTable.NAME, contentValues, "_id = ?",
                arrayOf( "${arguments!!.getInt("id_note")}" ))
        } else {
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            if (editText.text.isNotEmpty()) {
                contentValues.put(NoteSchema.NoteTable.Cols.FOLDER_ID, arguments?.getInt("id_folder"))
                store.insert(NoteSchema.NoteTable.NAME, null, contentValues)
            }
        }
    }

    private fun getDateFormat() : String {
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH).format(Date())
    }
}