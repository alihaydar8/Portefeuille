package com.example.myapplication

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ajouter.*
import java.util.*


class ajouter : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter)

        var helper = MyDBHelper(applicationContext)
        var db2 = helper.readableDatabase

        val n1 = findViewById<TextView>(R.id.n1)
        val back2= findViewById<Button>(R.id.back2)
        val Bajouter = findViewById<Button>(R.id.Bajouter)
        val typee = findViewById<TextView>(R.id.typee)
        val descriptivee = findViewById<TextView>(R.id.descriptivee)
        val prixx = findViewById<TextView>(R.id.prixx)



        val intent = this.intent
        val nom2 = intent.getStringExtra("nom1")
        val prenom2 = intent.getStringExtra("prenom1")

//        n1.text = nom2.toString().toLowerCase() +" "+ prenom2.toString().toUpperCase()



        var  rs2 = db2.rawQuery("SELECT * FROM AJOUTER ",null)
             prixx.text ="+"
            Toast.makeText(this, "ajouter", LENGTH_SHORT).show()

                Bajouter.setOnClickListener {
                    var cv = ContentValues()
                    cv.put("TA",typee.text.toString())
                    cv.put("DA",descriptivee.text.toString())
                    cv.put("PA",prixx.toString())
                    cv.put("DateA", Calendar.getInstance().time.toString())
                    db2.insert("AJOUTER",null,cv)

                    rs2.requery()
                    var ad = AlertDialog.Builder(this)
                    ad.setTitle("Add Record")
                    ad.setMessage("Record Inserted Successfully")
                    ad.setPositiveButton("ok",DialogInterface.OnClickListener{dialogInterface, i ->
                        prixx.text ="+"
                        descriptivee.text =""
                        typee.text =""
                        typee.requestFocus()
                    })
                    ad.show()
                }


        back2.setOnClickListener {
               startActivity(Intent(this, compte::class.java))
            
           }


    }
}
