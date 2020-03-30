package com.job4j.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return folders.size
    }
}