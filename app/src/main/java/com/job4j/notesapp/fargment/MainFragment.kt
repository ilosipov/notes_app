package com.job4j.notesapp.fargment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R

/**
 * Класс MainFragment - главное представление
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class MainFragment : Fragment() {
    private val log = "MainFragment"

    private lateinit var bottomBar : BottomAppBar
    private lateinit var addBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        Log.d(log, "onCreateView: initialization MainFragment.")

        bottomBar = view.findViewById(R.id.bottom_bar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(bottomBar)
        addBtn = view.findViewById(R.id.btn_add)
        addBtn.setOnClickListener(this::onClickAdd)

        return view
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAdd(v: View) {
        Log.d(log, "onClickAdd: click button.")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_left -> { Log.d(log, "onClick: click btn left.") }
            R.id.btn_right -> { Log.d(log, "onClick: click btn right.") }
        }
        return super.onOptionsItemSelected(item)
    }
}