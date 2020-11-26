package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class retire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retire)
        var retire = findViewById<Button>(R.id.retirer)
        var typee = findViewById<TextView>(R.id.typee)
        var descriptivee = findViewById<TextView>(R.id.descriptivee)
        var prixx = findViewById<TextView>(R.id.prix)

        retire.setOnClickListener {
            var i5 = Intent(this, compte::class.java)
            i5.putExtra("typee", typee.text.toString())
            i5.putExtra("prixx", prixx.text)
            i5.putExtra("descriptive", descriptivee.text.toString())
            startActivity(i5)
        }
    }
}