package Activity.Compte
import com.example.myapplication.R
 import Activity.Ajouter.Ajouter
import Activity.Ajouter.Listeajouter
 import Activity.Depense.Listeretirer
import Activity.Depense.Retirer
import DataBase.MyDBHelper
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
 import java.text.SimpleDateFormat
import java.util.*

class compte : AppCompatActivity() {
    lateinit var db1: SQLiteDatabase
    lateinit var db2: SQLiteDatabase
    lateinit var rs1: Cursor
    lateinit var rs2: Cursor
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte)
        
        var helper = MyDBHelper(applicationContext)
        db1 = helper.writableDatabase
        db2 = helper.writableDatabase

        val Binfo = findViewById<ImageButton>(R.id.Binfo)
        val back = findViewById<ImageButton>(R.id.back)
        val signeout = findViewById<ImageButton>(R.id.signeout)
        val Bplus = findViewById<ImageButton>(R.id.Bplus)
        val Bmoin = findViewById<ImageButton>(R.id.Bmoin)
        val listD= findViewById<ImageButton>(R.id.listD)
        val listA= findViewById<ImageButton>(R.id.listA)
        val argent = findViewById<TextView>(R.id.argent)
        val argentA = findViewById<TextView>(R.id.argentA)
        val argentD = findViewById<TextView>(R.id.argentD)
        val month = findViewById<TextView>(R.id.month)
        val npom = findViewById<TextView>(R.id.npom)

        val ImageUser = findViewById<ImageView>(R.id.ImageUser)
        rs1 =db1.rawQuery("SELECT * FROM DEPENSE ", null)
        rs2 =db2.rawQuery("SELECT * FROM AJOUTER ", null)

 // mise a jour du somme de depense
         var countD= 0
        if (rs1.moveToFirst()) {
            do {
                countD = countD + rs1.getString(rs1.getColumnIndex("PD")).toInt()
            }
            while (rs1.moveToNext())
        }
 // mise a jour du somme ajouter
        var countA= 0
        if (rs2.moveToFirst()) {
            do {
                countA = countA + rs2.getString(rs2.getColumnIndex("PA")).toInt()
            }
            while (rs2.moveToNext())
        }
//sharePreferences pour les utilisers apres et faire la somme
        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
        val editor1 = sharedPreferences.edit()
        editor1.putInt("countA", countA)
        editor1.putInt("countD", countD)
        editor1.apply()
//sharePreferences recupere les donnees
        npom.text = sharedPreferences.getString("nom","").toString().toLowerCase() +" "+ sharedPreferences.getString("prenom","").toString().toUpperCase()
        argentA.text = sharedPreferences.getInt("countA",0).toString()+".00 USD"
        argentD.text = sharedPreferences.getInt("countD",0).toString()+".00 USD"
        argent.text = (sharedPreferences.getInt("countA",0) - sharedPreferences.getInt("countD",0)).toString() +".00 USD"
        month.text = SimpleDateFormat("dd/MMMM/YYYY hh:mm").format(Date())
// mettre la bonne photo selon le sex
        if (sharedPreferences.getString("Sex","").toString() == "man")
        {
           ImageUser.setImageResource(R.drawable.man)
        }
        if (sharedPreferences.getString("Sex","").toString() == "woman")
        {
            ImageUser.setImageResource(R.drawable.woman)
        }
// intent pour tous les autres activites
        Binfo.setOnClickListener {
            startActivity(Intent(this, Info::class.java))
        }
        Bplus.setOnClickListener {
                startActivity(Intent(this, Ajouter::class.java))
        }
        Bmoin.setOnClickListener {
                startActivity(Intent(this, Retirer::class.java))
        }
        listA.setOnClickListener {
            startActivity(Intent(this, Listeajouter::class.java))
        }
        listD.setOnClickListener {
            startActivity(Intent(this, Listeretirer::class.java))
        }
        back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

///DELETE TOUTS !!  ATTENTION
        signeout.setOnClickListener {

            val bye = AlertDialog.Builder(this)
            bye.setTitle("Delete Account")
            bye.setMessage("if u delete ur account u will lose all the data")
            bye.setPositiveButton("ok", DialogInterface.OnClickListener{ _, _ ->
                sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
                val editor :SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                db1.delete("DEPENSE",null,null)
                db2.delete("AJOUTER",null,null)

                startActivity(Intent(this,
                    MainActivity::class.java))
            })
            bye.show()
        }
    }
}

