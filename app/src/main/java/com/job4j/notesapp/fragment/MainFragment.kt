package com.job4j.notesapp.fragment

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.job4j.notesapp.R
import com.job4j.notesapp.activity.FoldersActivity
import com.job4j.notesapp.adapter.EntryAdapter
import com.job4j.notesapp.listener.EntryListener
import com.job4j.notesapp.adapter.SwipeToTransferCallback
import com.job4j.notesapp.model.Entry
import com.job4j.notesapp.store.EntryBaseHelper
import com.job4j.notesapp.store.EntrySchema
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Класс MainFragment - главное представление
 * @author Ilya Osipov (mailto:il.osipov.gm@gmail.com)
 * @since 25.03.2020
 * @version $Id$
 */

class MainFragment : Fragment() {
    private var calendar = Calendar.getInstance()
    private var entrys = ArrayList<Entry>()

    private lateinit var dateMain : TextView
    private lateinit var emptyText : TextView
    private lateinit var adapter: EntryAdapter
    private lateinit var store : SQLiteDatabase
    private lateinit var cardViewDate : CardView
    private lateinit var barBottom : BottomAppBar
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnAdd : FloatingActionButton

    private lateinit var animTop : Animation
    private lateinit var animBottom : Animation
    private lateinit var animCenter : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        store = EntryBaseHelper(context!!).writableDatabase

        animTop = AnimationUtils.loadAnimation(context, R.anim.anim_top)
        animBottom = AnimationUtils.loadAnimation(context, R.anim.anim_bottom)
        animCenter = AnimationUtils.loadAnimation(context, R.anim.anim_center)

        emptyText = view.findViewById(R.id.empty_text_main)
        cardViewDate = view.findViewById(R.id.card_view_date)
        recyclerView = view.findViewById(R.id.recycler_view_main)

        dateMain = view.findViewById(R.id.text_date)
        dateMain.text = setDateFormat(calendar.time)

        barBottom = view.findViewById(R.id.bottom_bar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(barBottom)

        btnAdd = view.findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this::onClickAdd)

        btnAdd.startAnimation(animBottom)
        barBottom.startAnimation(animBottom)
        recyclerView.startAnimation(animTop)
        cardViewDate.startAnimation(animTop)
        emptyText.startAnimation(animCenter)

        updateUI(dateMain.text.toString())
        return view
    }

    private fun updateUI(date: String) {
        entrys.clear()
        val cursor = store.query(
            EntrySchema.EntryTable.NAME,
            null, "date = ?", arrayOf(date),
            null, null, null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            entrys.add(Entry(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex(EntrySchema.EntryTable.Cols.DATE)),
                cursor.getString(cursor.getColumnIndex(EntrySchema.EntryTable.Cols.TEXT)),
                cursor.getInt(cursor.getColumnIndex(EntrySchema.EntryTable.Cols.CHECKED)) != 0
            ))
            cursor.moveToNext()
        }
        cursor.close()

        Collections.sort(entrys, Entry())
        adapter = context?.let { EntryAdapter(it, R.layout.view_entry, entrys) }!!
        adapter.setListener(object : EntryListener {
            override fun onClick(position: Int) { updateData(position) }
            override fun onClickDelete(position: Int) { deleteData(position) }
        })

        if (entrys.size != 0) {
            recyclerView.visibility = View.VISIBLE
            emptyText.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            emptyText.visibility = View.VISIBLE
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : SwipeToTransferCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                updateItemDate(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun updateItemDate(position: Int) {
        val scrollPosition = recyclerView.layoutManager
            ?.onSaveInstanceState()
        val contentValues = ContentValues()
        contentValues.put(EntrySchema.EntryTable.Cols.DATE, setDateFormat(
            setStringFormat(entrys[position].date)))
        store.update(EntrySchema.EntryTable.NAME, contentValues, "_id = ?",
            arrayOf("${entrys[position].id}"))

        entrys.remove(entrys[position])
        Collections.sort(entrys, Entry())
        adapter.notifyItemRemoved(position)
        recyclerView.adapter = adapter
        recyclerView.layoutManager?.onRestoreInstanceState(scrollPosition)
    }

    private fun updateData(position: Int) {
        val scrollPosition = recyclerView.layoutManager
            ?.onSaveInstanceState()
        entrys[position].checked = !entrys[position].checked

        val contentValues = ContentValues()
        contentValues.put(EntrySchema.EntryTable.Cols.CHECKED, entrys[position].checked)
        store.update(EntrySchema.EntryTable.NAME, contentValues, "_id = ?",
            arrayOf("${entrys[position].id}"))

        Collections.sort(entrys, Entry())
        adapter.notifyItemChanged(position)
        recyclerView.adapter = adapter
        recyclerView.layoutManager
            ?.onRestoreInstanceState(scrollPosition)
    }

    private fun deleteData(position: Int) {
        val scrollPosition = recyclerView.layoutManager
            ?.onSaveInstanceState()
        store.delete(EntrySchema.EntryTable.NAME, "_id = ?", arrayOf("${entrys[position].id}"))
        entrys.remove(entrys[position])

        Collections.sort(entrys, Entry())
        adapter.notifyItemChanged(position)
        recyclerView.adapter = adapter
        recyclerView.layoutManager?.onRestoreInstanceState(scrollPosition)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickAdd(v: View) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, AddEntryFragment()
                .newInstance(dateMain.text.toString()))
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun updateListByDate() {
        dateMain.text = setDateFormat(calendar.time)
        updateUI(dateMain.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_left -> {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH).minus(1))
                updateListByDate()
            }
            R.id.btn_right -> {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH).plus(1))
                updateListByDate()
            }
            R.id.btn_folders -> {
                startActivity(Intent(context, FoldersActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDateFormat(date: Date) : String {
        return SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date.time)
    }

    private fun setStringFormat(date: String) : Date {
        val cal = Calendar.getInstance()
        cal.time = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(date)!!
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH).plus(1))
        return cal.time
    }
}