package com.example.lab2.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView.MultiChoiceModeListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.util.contains
import com.example.lab2.common.Globals
import com.example.lab2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class ContentListActivity : AppCompatActivityEx(),MultiChoiceModeListener {
    //private val contentPreferenceKey = "content_of_${Globals.instance.loggedInUserName}"
    private lateinit var contentListModal : ArrayAdapter<String>
    private lateinit var contentListRegular : ArrayAdapter<String>
    private lateinit var btnAction : FloatingActionButton
    private lateinit var selectedItems : Array<Boolean>
    private lateinit var listView : ListView
    private var contentListData = ArrayList<String>()
    private var counter = 0
    private var isContentUpdated = false

    private fun btnActionAddOnClick(){
        contentListData.add("Element$counter")
        contentListRegular.notifyDataSetChanged()
        counter++
        isContentUpdated = true
    }

    private fun btnActionRemoveOnClick(listView : ListView){
        val checkedPositions = listView.checkedItemPositions
        val copyOf = ArrayList<String>()
        for (i in contentListData.indices) {
            if (!checkedPositions.contains(i)) {
                copyOf.add(contentListData[i])
            } else {
                listView.setItemChecked(i, false)
            }
        }
        contentListData.clear()
        contentListData += copyOf
        contentListModal.notifyDataSetChanged()

        isContentUpdated = true
        selectedItems = Array(contentListData.size) { false }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        selectedItems = Array(contentListData.size) { false }
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

    private fun initComponents(){
        listView = findViewById(R.id.contentListView)
        contentListRegular = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            contentListData
        )

        contentListModal = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_multiple_choice,
            contentListData
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

    private fun readContentList(){
        val gson = Gson()
        val jsonText = dbHelper.getUserContentListText(Globals.instance.loggedInUserName) ?: return
        if (jsonText.isEmpty()){
            return
        }
        contentListData = gson.fromJson(jsonText,contentListData.javaClass)
    }

    private fun writeContentList(){
        if (isContentUpdated) {
            val gson = Gson()
            val jsonText = gson.toJson(contentListData)
            dbHelper.setUserContentList(Globals.instance.loggedInUserName,jsonText)
            isContentUpdated = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)
        readContentList()
        initComponents()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun menuActionLogout(){
        finish()
    }

    private fun menuActionRemoveUser(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.txt_continue_remove_user)
            .setPositiveButton(R.string.txt_cancel) { _, _ ->

            }
            .setNegativeButton(R.string.txt_yes) { _, _ ->
                dbHelper.deleteUserWithUserName(Globals.instance.loggedInUserName)
                finish()
            }
        builder.create().show()
    }

    private fun startSettingsActivity(){
        val intent = Intent(this, SettingsActivity::class.java).apply {}
        startActivity(intent)
    }

    private fun menuActionSettings(){
        startSettingsActivity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {
                menuActionLogout()
                true
            }
            R.id.action_settings ->{
                menuActionSettings()
                true
            }
            R.id.action_remove_user ->{
                menuActionRemoveUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        writeContentList()
    }

    override fun onDestroy() {
        super.onDestroy()
        writeContentList()
    }
}
