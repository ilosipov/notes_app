package com.job4j.notesapp.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R
import com.job4j.notesapp.adapter.ColorAdapter
import com.job4j.notesapp.listener.OnClickItemListener
import com.job4j.notesapp.listener.PositiveDialogListener

/**
 * Класс UpdateColorFolderDialog - диалоговое окно для изменения цвета папки
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 31.03.2020
 * @version $Id$
 */

class UpdateColorFolderDialog : DialogFragment() {
    private lateinit var listener : PositiveDialogListener
    private var colors : List<String> = arrayListOf(
        "#0000FF", "#FF0000", "#FFA500", "#00FF00", "#FFFF00", "#C8A2C8", "#00BFFF", "#660099",
        "#008080", "#CD853F", "#CC8899", "#3A75C4", "#FDE910", "#F3C3F3", "#C0C0C0", "#ACE1AF"
    )

    fun newInstance(position: Int) : UpdateColorFolderDialog {
        val bundle = Bundle()
        bundle.putInt("position_folder", position)

        val updateColorFolderDialog = UpdateColorFolderDialog()
        updateColorFolderDialog.arguments = bundle
        return updateColorFolderDialog
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_update_color_folder, null)

        val btnNegative = view.findViewById<TextView>(R.id.btn_negative_update_folder)
        btnNegative.setOnClickListener { this.dismiss() }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_colors)
        recyclerView.layoutManager = GridLayoutManager(context, 6)

        val adapter = ColorAdapter(context!!, R.layout.view_color, colors)
        adapter.setListener(object :
            OnClickItemListener {
            override fun onClick(position: Int) {
                listener.onClickPositive(this@UpdateColorFolderDialog,
                    arguments!!.getInt("position_folder"), "", colors[position])
            }
            override fun onLongClick(position: Int) {}
        })
        recyclerView.adapter = adapter

        val alertDialog = activity?.let { AlertDialog.Builder(it) }
        alertDialog?.setView(view)

        val alertDialogCreate = alertDialog?.create()
        if (alertDialogCreate?.window != null) {
            alertDialogCreate.window?.setBackgroundDrawable(ColorDrawable(0))
        }

        return alertDialogCreate as Dialog
    }

    fun setListener(listener: PositiveDialogListener) {
        this.listener = listener
    }
}