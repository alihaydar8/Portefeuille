package com.example.myapplication

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_ajouter.*
import kotlinx.android.synthetic.main.activity_retirer.*
import java.util.*

class retirer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retirer)

        var helper = MyDBHelper(applicationContext)
        var db1 = helper.readableDatabase

        val n2 = findViewById<TextView>(R.id.n2)
        val back1= findViewById<Button>(R.id.back1)
        val Bretirer = findViewById<Button>(R.id.Bretirer)
        val type = findViewById<TextView>(R.id.type)
        val descriptive = findViewById<TextView>(R.id.descriptive)
        val prix = findViewById<TextView>(R.id.prix)


        val intent = this.intent
        var nom3 = intent.getStringExtra("nom1")
        var prenom3 = intent.getStringExtra("prenom1")

//        n2.text = nom3.toString().toLowerCase() +" "+ prenom3.toString().toUpperCase()


        var  rs1 = db1.rawQuery("SELECT * FROM DEPENSE ",null)
         prix.text ="-"
         Toast.makeText(this, "depense", LENGTH_SHORT).show()

        Bretirer.setOnClickListener {
            var cv = ContentValues()
            cv.put("TD",type.text.toString())
            cv.put("DD",descriptive.text.toString())
            cv.put("PD",prix.toString())
            cv.put("DateD", Calendar.getInstance().time.toString())
            db1.insert("DEPENSE",null,cv)

            rs1.requery()
            var ad = AlertDialog.Builder(this)
            ad.setTitle("Add Record")
            ad.setMessage("Record Inserted Successfully")
            ad.setPositiveButton("ok", DialogInterface.OnClickListener{ dialogInterface, i ->
                prix.text ="-"
                descriptive.text =""
                type.text =""
                type.requestFocus()
            })
            ad.show()
        }


        back1.setOnClickListener {
            startActivity(Intent(this, compte::class.java))
        }


    }
}
