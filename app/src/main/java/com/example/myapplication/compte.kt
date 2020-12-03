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
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_compte2.*
import kotlinx.android.synthetic.main.activity_main.*

class compte : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte2)

        /* val fragments = ArrayList<Fragment>()
        fragments.add(CustomFragment1())
        fragments.add(CustomFragment2())

        val viewPagerAdapter = SlidePageAdapter(fragments, supportFragmentManager)
        vp_main.adapter = viewPagerAdapter*/
        val Binfo = findViewById<ImageButton>(R.id.Binfo)
        val Back = findViewById<ImageButton>(R.id.back)
        val Bplus = findViewById<ImageButton>(R.id.Bplus)
        val Bmoin = findViewById<ImageButton>(R.id.Bmoin)
        val listD= findViewById<ImageButton>(R.id.listD)
        val listA= findViewById<ImageButton>(R.id.listA)
        var argent = findViewById<TextView>(R.id.argent)
        var argentA = findViewById<TextView>(R.id.argentA)
        var argentD = findViewById<TextView>(R.id.argentD)
        val npom = findViewById<TextView>(R.id.npom)



        val intent = this.intent
        var nom1 = intent.getStringExtra("nom")
        var prenom1 = intent.getStringExtra("prenom")
        var mail1 = intent.getStringExtra("mail")
        if(npom.text == "")
        {npom.text = nom1.toString().toLowerCase() +" "+ prenom1.toString().toUpperCase()}

        //  val np = nom1.toString() + " " + prenom1.toString()
        //  npom.text = np



      //  Binfo.setOnClickListener {

     //       val i1 = Intent(this,info::class.java)
     //       //  i2.putExtra("nom",np)
      //      startActivity(i2)
     //   }

        Bplus.setOnClickListener {

            val i2 = Intent(this,ajouter::class.java)
            i2.putExtra("nom1",nom1)
            i2.putExtra("prenom1",prenom1)
                startActivity(i2)
        }
        Bmoin.setOnClickListener {

            val i3 = Intent(this,retirer::class.java)
            i3.putExtra("nom1",nom1)
            i3.putExtra("prenom1",prenom1)
                startActivity(i3)
        }
        listA.setOnClickListener {

            val i4 = Intent(this,listeajouter::class.java)
            i4.putExtra("nom1",nom1)
            i4.putExtra("prenom1",prenom1)
            startActivity(i4)
        }
        listD.setOnClickListener {

            val i5 = Intent(this,listeretirer::class.java)
            i5.putExtra("nom1",nom1)
            i5.putExtra("prenom1",prenom1)
            startActivity(i5)
        }
        back.setOnClickListener {

            startActivity(Intent(this,MainActivity::class.java))
        }


    }}

