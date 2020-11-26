package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ajouter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter)

        var Bajjouter = findViewById<Button>(R.id.ajjouter)
        var type = findViewById<TextView>(R.id.type)
        var descriptive = findViewById<TextView>(R.id.descriptive)
        var prix = findViewById<TextView>(R.id.prix)

        Bajjouter.setOnClickListener {
            var i4 = Intent(this,compte::class.java)
            i4.putExtra("type",type.text.toString())
            i4.putExtra("prix",prix.text)
            i4.putExtra("descriptive",descriptive.text.toString())
            startActivity(i4)
        }
    }
}