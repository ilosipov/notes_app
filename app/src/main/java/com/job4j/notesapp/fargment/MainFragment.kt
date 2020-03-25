package com.job4j.notesapp.fargment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Класс MainFragment - главное представление
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class MainFragment : Fragment() {
    private val log = "MainFragment"

    private var calendar = Calendar.getInstance()

    private lateinit var dateMain : TextView
    private lateinit var barBottom : BottomAppBar
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnAdd : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        Log.d(log, "onCreateView: initialization MainFragment.")

        dateMain = view.findViewById(R.id.text_date)
        dateMain.text = setDateFormat(calendar.time)

        recyclerView = view.findViewById(R.id.recycler_view_main)

        barBottom = view.findViewById(R.id.bottom_bar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(barBottom)

        btnAdd = view.findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this::onClickAdd)

        return view
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAdd(v: View) {
        Log.d(log, "onClickAdd: click button.")
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, AddEntryFragment()
                .newInstance(dateMain.text.toString()))
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_left -> {
                Log.d(log, "onClick: click btn left.")
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH).minus(1))
                dateMain.text = setDateFormat(calendar.time)
            }
            R.id.btn_right -> {
                Log.d(log, "onClick: click btn right.")
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH).plus(1))
                dateMain.text = setDateFormat(calendar.time)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDateFormat(date: Date) : String {
        return SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date.time)
    }
}