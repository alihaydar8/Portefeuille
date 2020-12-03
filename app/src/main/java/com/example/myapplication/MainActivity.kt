package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<Button>(R.id.login)
        val nom = findViewById<TextView>(R.id.nom)
        val prenom = findViewById<TextView>(R.id.prenom)
        val mail = findViewById<TextView>(R.id.mail)

        login.setOnClickListener {
            if(!nom.text.toString().isNullOrEmpty() && !prenom.text.toString().isNullOrEmpty() && !mail.text.toString().isNullOrEmpty())
            {val i = Intent(this,compte::class.java)
                i.putExtra("nom",nom.text.toString())
                i.putExtra("prenom",prenom.text.toString())
                i.putExtra("mail",prenom.text.toString())
                startActivity(i)}
            else {
                Toast.makeText(this, "fill the information ",
                    Toast.LENGTH_SHORT).show()}
        }
    }
}