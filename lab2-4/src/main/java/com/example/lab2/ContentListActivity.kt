package com.example.lab2

import android.content.Context
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
import com.google.gson.Gson


class ContentListActivity : AppCompatActivityWithLog(),MultiChoiceModeListener {
    private lateinit var contentListModal : ArrayAdapter<String>
    private lateinit var contentListRegular : ArrayAdapter<String>
    private lateinit var btnAction : FloatingActionButton
    private lateinit var selectedItems : Array<Boolean>
    private lateinit var listView : ListView
    private var contentListData = ArrayList<String>()
    private var counter = 0

    private fun btnActionAddOnClick(){
        contentListData.add("Element$counter")
        contentListRegular.notifyDataSetChanged()
        counter++
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

    private fun logExtras(){
        val arguments = intent.extras
        if (arguments != null)
            Log.i("App Logger", "Login: "+arguments.get(Globals.EXTRA_MESSAGE).toString())
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

    private fun readContentFromPreferences(){
        val sharedPrefs = this.getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonText = sharedPrefs.getString("content",null) ?: return

        contentListData = gson.fromJson(jsonText,contentListData.javaClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)
        logExtras()
        readContentFromPreferences()
        initComponents()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun clearLoginInPreferences(){
        val sharedPref = this.getSharedPreferences(Globals.APP_SETTINGS_PREFERENCE_KEY,0)
        val editor = sharedPref.edit()
        editor.putBoolean("is_logged_in",false)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                clearLoginInPreferences()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun writeContentToPreferences(){
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            val gson = Gson()
            val jsonText = gson.toJson(contentListData)
            putString("content",jsonText)
            apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        writeContentToPreferences()
    }
}
