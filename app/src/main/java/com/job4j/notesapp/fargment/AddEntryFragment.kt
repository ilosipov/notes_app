package com.job4j.notesapp.fargment

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.job4j.notesapp.R
import com.job4j.notesapp.store.EntryBaseHelper
import com.job4j.notesapp.store.EntrySchema

/**
 * Класс AddEntryFragment - добавляет текущую запись
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class AddEntryFragment : Fragment() {
    private val log = "AddEntryFragment"

    private lateinit var store : SQLiteDatabase
    private lateinit var imm : InputMethodManager
    private lateinit var btnPositive : ImageView
    private lateinit var btnNegative : ImageView
    private lateinit var dateEntry : TextView
    private lateinit var editText : EditText

    fun newInstance(date: String) : Fragment {
        val bundle = Bundle()
        bundle.putString("date_entry", date)

        val fragment = AddEntryFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_entry, container, false)
        Log.d(log, "onCreateView: initialization AddEntryFragment.")

        store = EntryBaseHelper(context!!).writableDatabase

        imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        editText = view.findViewById(R.id.edit_text_entry)
        editText.requestFocus()

        dateEntry = view.findViewById(R.id.text_date_add)
        dateEntry.text = arguments?.get("date_entry").toString()

        btnPositive = view.findViewById(R.id.btn_positive_entry)
        btnPositive.setOnClickListener(this::onClickPositive)

        btnNegative = view.findViewById(R.id.btn_negative_entry)
        btnNegative.setOnClickListener(this::onClickNegative)

        return view
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickPositive(v: View) {
        Log.d(log, "onClickPositive: click positive.")
        imm.hideSoftInputFromWindow(editText.windowToken, 0)

        val contentValues = ContentValues()
        contentValues.put(EntrySchema.EntryTable.Cols.DATE, arguments?.get("date_entry").toString())
        contentValues.put(EntrySchema.EntryTable.Cols.TEXT, editText.text.toString().trim())
        store.insert(EntrySchema.EntryTable.NAME, null, contentValues)

        activity?.supportFragmentManager?.popBackStack()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickNegative(v: View) {
        Log.d(log, "onClickNegative: click negative.")
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
        activity?.supportFragmentManager?.popBackStack()
    }
}