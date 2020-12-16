package Activity.Depense

import Activity.Compte.compte

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import DataBase.MyDBHelper
import com.example.myapplication.R

import kotlinx.android.synthetic.main.activity_listeretirer.*

import java.text.SimpleDateFormat
import java.util.*

class Listeretirer : AppCompatActivity() {
    lateinit var db1: SQLiteDatabase
    lateinit var rs1: Cursor
    lateinit var adapter: SimpleCursorAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listeretirer)
        val n4 = findViewById<TextView>(R.id.n4)
        val searchView1 = findViewById<SearchView>(R.id.searchView1)
        val listview1 = findViewById<ListView>(R.id.listview1)
        val back3 = findViewById<ImageButton>(R.id.back3)

//SharedPreference (Recuperer)
        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
        n4.text = sharedPreferences.getString("nom","").toString().toLowerCase() +" "+ sharedPreferences.getString("prenom","").toString().toUpperCase()
        argentLD.text = (sharedPreferences.getInt("countA",0) - sharedPreferences.getInt("countD",0)).toString() +".00 USD"

        if (sharedPreferences.getString("Sex","").toString() == "man")
        {
            ImageUserLD.setImageResource(R.drawable.man)
        }
        if (sharedPreferences.getString("Sex","").toString() == "woman")
        {
            ImageUserLD.setImageResource(R.drawable.woman)
        }
        monthLD.text = SimpleDateFormat("dd/MMMM/YYYY hh:mm").format(Date())

//recuperer la Database DEPENSE dans une list
        val helper = MyDBHelper(applicationContext)
        db1 = helper.writableDatabase
        rs1 = db1.rawQuery("SELECT * FROM DEPENSE ORDER BY DateD DESC", null)

        adapter = SimpleCursorAdapter(applicationContext,
            R.layout.fragment_card, rs1,
            arrayOf("TD","PD","DD","DateD"), intArrayOf(
                R.id.typeee,
                R.id.prixxx,
                R.id.descriptiveee,
                R.id.dateee
            ), 0)
        listview1.adapter = adapter
        adapter.notifyDataSetChanged()

  /// la petite phrase dans la searchview
        searchView1.queryHint = "Search Amond ${rs1.count} Record"

/// faire fonctionner la searchview
        searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs1 = db1.rawQuery("SELECT * FROM DEPENSE WHERE TD LIKE '%${p0}%' OR DD LIKE '%${p0}%' OR TD LIKE '%${p0}%'OR DateD LIKE '%${p0}%'", null)
                adapter.changeCursor(rs1)

                return false
            }
        })

 //recurer en arriere
        back3.setOnClickListener {
            startActivity(Intent(this, compte::class.java))
        }
        registerForContextMenu(listview1)


    }

//Set On Click
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(101, 11, 2, "DELETE")
        menu?.add(101, 12, 1, "UPDATE")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val searchView1 = findViewById<SearchView>(R.id.searchView1)
  // DELETE une ligne DataBase
        if (item.itemId == 11) {
            db1.delete("DEPENSE", "_id = ?", arrayOf(rs1.getString(0)))
            rs1.requery()
            adapter.notifyDataSetChanged()
            searchView1.queryHint = "Search Amond ${rs1.count} Record"
        }
 //UPDATE une ligne DataBase
        if(item.itemId == 12)
        {
            val editor = sharedPreferences.edit()
            editor.putString("updateTD", rs1.getString(rs1.getColumnIndex("TD")))
            editor.putString("updatePD", rs1.getString(rs1.getColumnIndex("PD")))
            editor.putString("updateDD", rs1.getString(rs1.getColumnIndex("DD")))
            editor.putString("updateDateD", rs1.getString(rs1.getColumnIndex("DateD")))
            editor.apply()
            db1.delete("DEPENSE", "_id = ?", arrayOf(rs1.getString(0)))
            rs1.requery()
            adapter.notifyDataSetChanged()
            searchView1.queryHint = "Search Amond ${rs1.count} Record"
            startActivity(Intent(this, Retirer::class.java))
        }
        return super.onContextItemSelected(item)

    }
}
