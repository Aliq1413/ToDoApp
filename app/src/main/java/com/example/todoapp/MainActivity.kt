package com.example.todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var items: ArrayList<ToDoItem>
    private lateinit var rvItems: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = arrayListOf()

        rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvAdapter = RVAdapter(items)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            customDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemsDeleted = 0
        for(i in items){
            if(i.checked){itemsDeleted++}
        }

        if(itemsDeleted > 0){
            Toast.makeText(this, "$itemsDeleted items deleted", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "No items selected", Toast.LENGTH_LONG).show()
        }
        rvAdapter.deleteItems()
        return super.onOptionsItemSelected(item)
    }

    private fun customDialog(){
        val dialogBuilder = AlertDialog.Builder(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val toDo = EditText(this)
        toDo.hint = "Enter to-do item"
        layout.addView(toDo)

        dialogBuilder.setPositiveButton("ADD", DialogInterface.OnClickListener {
                dialog, id -> items.add(ToDoItem(toDo.text.toString()))
        })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("New Item")
        alert.setView(layout)
        alert.show()
    }
}