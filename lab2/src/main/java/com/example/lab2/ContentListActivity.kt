package com.example.lab2

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView.MultiChoiceModeListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.util.contains
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContentListActivity : AppCompatActivityWithLog(),MultiChoiceModeListener {
    private lateinit var contentListModal : ArrayAdapter<String>
    private lateinit var contentListRegular : ArrayAdapter<String>
    private lateinit var btnAction : FloatingActionButton
    private lateinit var selectedItems : Array<Boolean>
    private lateinit var listView : ListView
    private var counter = 0

    private fun btnActionAddOnClick(){
        Globals.contentListData.add("Element$counter")
        contentListRegular.notifyDataSetChanged()
        counter++
    }

    private fun btnActionRemoveOnClick(listView : ListView){
        val checkedPositions = listView.checkedItemPositions
        val copyOf = ArrayList<String>()
        for (i in Globals.contentListData.indices) {
            if (!checkedPositions.contains(i)) {
                copyOf.add(Globals.contentListData[i])
            } else {
                listView.setItemChecked(i, false)
            }
        }
        Globals.contentListData.clear()
        Globals.contentListData += copyOf
        contentListModal.notifyDataSetChanged()

        selectedItems = Array(Globals.contentListData.size) { false }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        selectedItems = Array(Globals.contentListData.size) { false }
        btnAction.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(android.R.color.holo_red_dark,null))

        btnAction.setImageResource(android.R.drawable.ic_delete)
        btnAction.setOnClickListener{
            btnActionRemoveOnClick(listView)

        }
        listView.adapter = contentListModal
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        btnAction.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(android.R.color.holo_blue_light,null))

        btnAction.setImageResource(android.R.drawable.ic_input_add)
        btnAction.setOnClickListener{
            btnActionAddOnClick()
        }
        listView.adapter = contentListRegular
    }

    override fun onItemCheckedStateChanged(
        mode: ActionMode?,
        position: Int,
        id: Long,
        checked: Boolean
    ) {
        //selectedItems[position] = checked
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = intent.extras
        if (arguments != null)
            Log.i("App Logger", "Login: "+arguments.get(Globals.EXTRA_MESSAGE).toString())

        setContentView(R.layout.activity_content_list)

        listView = findViewById(R.id.contentListView)
        contentListRegular = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Globals.contentListData
        )

        contentListModal = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            Globals.contentListData
        )

        listView.adapter = contentListRegular
        btnAction = findViewById(R.id.btnAction)

        btnAction.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(android.R.color.holo_blue_light,null))

        btnAction.setOnClickListener{
            btnActionAddOnClick()
        }

        listView.setMultiChoiceModeListener(this)

    }
}
