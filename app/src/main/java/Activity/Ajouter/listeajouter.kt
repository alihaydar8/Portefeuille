package Activity.Ajouter

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
import android.content.ContentValues
import kotlinx.android.synthetic.main.activity_ajouter.*
import kotlinx.android.synthetic.main.activity_listeajouter.*

import java.text.SimpleDateFormat
import java.util.*

class listeajouter : AppCompatActivity() {
    lateinit var db2: SQLiteDatabase
    lateinit var rs2: Cursor
    lateinit var adapter: SimpleCursorAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listeajouter)
        val n3 = findViewById<TextView>(R.id.n3)
        val searchView2 = findViewById<SearchView>(R.id.searchView2)
        val listview2 = findViewById<ListView>(R.id.listview2)
        val back4 = findViewById<ImageButton>(R.id.back4)
//SharedPreference (Recuperer)
        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
        n3.text = sharedPreferences.getString("nom","").toString().toLowerCase() +" "+ sharedPreferences.getString("prenom","").toString().toUpperCase()
        argentLA.text = (sharedPreferences.getInt("countA",0) - sharedPreferences.getInt("countD",0)).toString() +".00 USD"

        if (sharedPreferences.getString("Sex","").toString() == "man")
        {
            ImageUserLA.setImageResource(R.drawable.man)
        }
        if (sharedPreferences.getString("Sex","").toString() == "woman")
        {
            ImageUserLA.setImageResource(R.drawable.woman)
        }
        monthLA.text = SimpleDateFormat("dd/MMMM/YYYY hh:mm").format(Date())

//recuperer la Database AJOUTER dans une list
        val helper = MyDBHelper(applicationContext)
        db2 = helper.writableDatabase
        rs2 = db2.rawQuery("SELECT * FROM AJOUTER ORDER BY DateA DESC ", null)

        adapter = SimpleCursorAdapter(applicationContext,
            R.layout.fragment_card, rs2,
                arrayOf("TA","PA","DA","DateA"), intArrayOf(
                R.id.typeee,
                R.id.prixxx,
                R.id.descriptiveee,
                R.id.dateee
            ), 0)
        listview2.adapter = adapter
        adapter.notifyDataSetChanged()
  /// la petite phrase dans la searchview
        searchView2.queryHint = "Search Amond ${rs2.count} Record"

/// faire fonctionner la searchview
        searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs2= db2.rawQuery("SELECT * FROM AJOUTER WHERE TA LIKE '%${p0}%' OR DA LIKE '%${p0}%' OR PA LIKE '%${p0}%' OR DateA LIKE '%${p0}%'", null)
                adapter.changeCursor(rs2)
                return false
            }
        })

//recurer en arriere
        back4.setOnClickListener {
            startActivity(Intent(this, compte::class.java))
        }
        registerForContextMenu(listview2)
    }
 //Set On Click
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(101, 11, 2, "DELETE")
        menu?.add(101, 12, 1, "UPDATE")

    }
//DELETE une ligne du DataBase
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val searchView2 = findViewById<SearchView>(R.id.searchView2)
        if (item.itemId == 11) {
            db2.delete("AJOUTER", "_id = ?", arrayOf(rs2.getString(0)))
            rs2.requery()
            adapter.notifyDataSetChanged()
            searchView2.queryHint = "Search Amond ${rs2.count} Record"
        }
  //UPDATE une ligne de DataBase
        if(item.itemId == 12) {

            val editor = sharedPreferences.edit()
            editor.putString("updateTA", rs2.getString(rs2.getColumnIndex("TA")))
            editor.putString("updatePA", rs2.getString(rs2.getColumnIndex("PA")))
            editor.putString("updateDA", rs2.getString(rs2.getColumnIndex("DA")))
            editor.putString("updateDateA", rs2.getString(rs2.getColumnIndex("DateA")))
            editor.apply()
            db2.delete("AJOUTER", "_id = ?", arrayOf(rs2.getString(0)))
            rs2.requery()
            adapter.notifyDataSetChanged()
            searchView2.queryHint = "Search Amond ${rs2.count} Record"
            startActivity(Intent(this,ajouter::class.java))
        }

        return super.onContextItemSelected(item)
    }

}
