package com.job4j.notesapp.activity

import androidx.fragment.app.Fragment
import com.job4j.notesapp.BaseActivity
import com.job4j.notesapp.fragment.FoldersFragment

/**
 * Класс FoldersActivity - запускает FoldersFragment
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class FoldersActivity : BaseActivity() {

    override fun createFragment(): Fragment {
        return FoldersFragment()
    }
}