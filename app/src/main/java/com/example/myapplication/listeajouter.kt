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
import kotlinx.android.synthetic.main.activity_listeajouter.*

class listeajouter : AppCompatActivity() {
            lateinit var db2: SQLiteDatabase
            lateinit var rs2: Cursor
            lateinit var adapter1: SimpleCursorAdapter

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_listeretirer)

                val n3 = findViewById<TextView>(R.id.n3)
                val listview2 = findViewById<ListView>(R.id.listview2)
                val searchView2 = findViewById<SearchView>(R.id.searchView2)
                val back4 = findViewById<ImageButton>(R.id.back4)

                val intent = this.intent
                var nom4 = intent.getStringExtra("nom1")
                var prenom4 = intent.getStringExtra("prenom1")


           //     n3.text = nom4.toString().toLowerCase() +" "+ prenom4.toString().toUpperCase()

                var helper = MyDBHelper(applicationContext)
                db2 = helper.writableDatabase
                rs2 = db2.rawQuery("SELECT * FROM AJOUTER ", null)

                adapter1 = SimpleCursorAdapter(applicationContext, R.layout.fragment_card, rs2,
                    arrayOf("TA","PA","DA","DateA"), intArrayOf(R.id.typeee, R.id.prixxx,R.id.descriptiveee,R.id.dateee), 0)
                listview2.adapter = adapter1

                registerForContextMenu(listview2)

                adapter1.notifyDataSetChanged()
                searchView2.queryHint = "Search Amond ${rs2.count} Record"


                searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        rs2 = db2.rawQuery("SELECT * FROM AJOUTER WHERE TD LIKE '%${p0}%' OR DD LIKE '%${p0}%'", null)
                        adapter1.changeCursor(rs2)
                        return false
                    }
                })
                back4.setOnClickListener {
                    startActivity(Intent(this, compte::class.java))
                }

                registerForContextMenu(listview2)
            }
            override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                super.onCreateContextMenu(menu, v, menuInfo)
                menu?.add(101, 11, 2, "DELETE")
                menu?.add(101, 12, 1, "UPDATE")

            }

            override fun onContextItemSelected(item: MenuItem): Boolean {
                val searchView2 = findViewById<SearchView>(R.id.searchView2)
                if (item.itemId == 11) {
                    db2.delete("AJOUTER", "_id = ?", arrayOf(rs2.getString(0)))
                    rs2.requery()
                    adapter1.notifyDataSetChanged()
                    searchView2.queryHint = "Search Amond ${rs2.count} Record"
                }
                if(item.itemId == 12)
                {

                }
                return super.onContextItemSelected(item)
            }
        }