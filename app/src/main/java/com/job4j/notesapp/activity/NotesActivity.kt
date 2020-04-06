package com.job4j.notesapp.activity

import androidx.fragment.app.Fragment
import com.job4j.notesapp.BaseActivity
import com.job4j.notesapp.fragment.NotesFragment

/**
 * Класс NotesActivity - запускает NotesFragment
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 01.04.2020
 * @version $Id$
 */

class NotesActivity : BaseActivity() {

    override fun createFragment(): Fragment {
        return NotesFragment().newInstance(
            intent.getIntExtra("id_folder", 0),
            intent.getStringExtra("name_folder")!!
        )
    }
}