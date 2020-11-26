package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<Button>(R.id.login)
        val nom = findViewById<TextView>(R.id.nom)
        val prenom = findViewById<TextView>(R.id.prenom)


        login.setOnClickListener {
            val i1 = Intent(this,compte::class.java)
            i1.putExtra("nom",nom.text.toString())
            i1.putExtra("prenom",prenom.text.toString())
            startActivity(i1)
        }
    }
}