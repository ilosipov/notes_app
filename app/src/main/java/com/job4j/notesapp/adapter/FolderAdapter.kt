package com.job4j.notesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.job4j.notesapp.R
import com.job4j.notesapp.model.Folder

/**
 * Класс FolderAdapter - адаптер папок
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 30.03.2020
 * @version $Id$
 */

class FolderAdapter(private var context: Context, private var resource: Int,
                    private var folders: List<Folder>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : FolderViewHolder {
        return FolderViewHolder(LayoutInflater.from(context).inflate(resource, parent, false))
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = folders[position]

        val name = holder.itemView.findViewById<TextView>(R.id.name_folder)
        name.text = folder.name

        val colorFolder = holder.itemView.findViewById<ImageView>(R.id.color_folder)
        colorFolder.setImageDrawable(ColorDrawable(Color.parseColor(folder.colorFolder)))
    }

    override fun getItemCount(): Int {
        return folders.size
    }
}