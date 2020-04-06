package com.job4j.notesapp.activity

import androidx.fragment.app.Fragment
import com.job4j.notesapp.BaseActivity
import com.job4j.notesapp.fragment.MainFragment

/**
 * Класс MainActivity - запускает MainFragment
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class MainActivity : BaseActivity() {

    override fun createFragment(): Fragment {
        return MainFragment()
    }
}
