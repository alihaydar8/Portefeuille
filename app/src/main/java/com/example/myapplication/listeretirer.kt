package com.example.myapplication

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_compte2.*

class listeretirer : AppCompatActivity() {
    lateinit var db1: SQLiteDatabase
    lateinit var rs1: Cursor
    lateinit var adapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listeretirer)
        val n4 = findViewById<TextView>(R.id.n4)
        val searchView1 = findViewById<SearchView>(R.id.searchView1)
        val listview1 = findViewById<ListView>(R.id.listview1)
        val back3 = findViewById<ImageButton>(R.id.back3)


        val intent = this.intent
        var nom5 = intent.getStringExtra("nom1")
        var prenom5 = intent.getStringExtra("prenom1")

//        n4.text = nom5.toString().toLowerCase() +" "+ prenom5.toString().toUpperCase()

        var helper = MyDBHelper(applicationContext)
        db1 = helper.writableDatabase
        rs1 = db1.rawQuery("SELECT * FROM DEPENSE ", null)

      //  listview1.visibility = View.VISIBLE
        adapter = SimpleCursorAdapter(applicationContext, R.layout.fragment_card, rs1,
            arrayOf("TD","PD","DD","DateD"), intArrayOf(R.id.typeee, R.id.prixxx,R.id.descriptiveee,R.id.dateee), 0)
        listview1.adapter = adapter
        adapter.notifyDataSetChanged()
        searchView1.queryHint = "Search Amond ${rs1.count} Record"


        searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs1 = db1.rawQuery("SELECT * FROM DEPENSE WHERE TD LIKE '%${p0}%' OR DD LIKE '%${p0}%'", null)
                adapter.changeCursor(rs1)
                return false
            }
        })


        back3.setOnClickListener {
            startActivity(Intent(this, compte::class.java))
        }
        registerForContextMenu(listview1)
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(101, 11, 2, "DELETE")
        menu?.add(101, 12, 1, "UPDATE")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val searchView1 = findViewById<SearchView>(R.id.searchView1)
        if (item.itemId == 11) {
            db1.delete("DEPENSE", "_id = ?", arrayOf(rs1.getString(0)))
            rs1.requery()
            adapter.notifyDataSetChanged()
            searchView1.queryHint = "Search Amond ${rs1.count} Record"
        }
        if(item.itemId == 12)
        {

        }
        return super.onContextItemSelected(item)
    }
}
