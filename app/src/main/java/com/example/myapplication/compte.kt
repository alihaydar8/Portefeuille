package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class compte : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte2)

        var Bplus = findViewById<ImageButton>(R.id.Bplus)
        var Bmoin = findViewById<ImageButton>(R.id.Bmoin)
        var plus = findViewById<TextView>(R.id.plus)
        var moin = findViewById<TextView>(R.id.moin)


        var argent = findViewById<TextView>(R.id.argent)
        val intent = this.intent
        var npom = findViewById<TextView>(R.id.npom)
        val nom1= intent.getStringExtra("nom")
        var prenom1= intent.getStringExtra("prenom")
        npom.text = nom1.toString() +" "+  prenom1.toString().toUpperCase()
        var typem=intent.getStringExtra("typee")
        var prixm= intent.getIntExtra("prixx",0)
        var descriptivem =intent.getStringExtra("descriptivee")


        Bplus.setOnClickListener{
            var i2= Intent(this,ajouter::class.java)
            startActivity(i2)
        }
        var dollar:Int ?=null
        var typep =intent.getStringExtra("type")
        var prixp = intent.getIntExtra("prix",0)
        var descriptivep =intent.getStringExtra("descriptive")
        Bmoin.setOnClickListener{
            var i3= Intent(this,retire::class.java)
            startActivity(i3)
        }


        dollar = (dollar?.plus(prixp)?.minus(prixm))
        var d:Int?=null
        d= d?.plus(prixp)
        plus.text =  prixp.toString()
        var dd:Int?=null
        dd= dd?.plus(prixm)
        moin.text =  dd.toString() +" $"
        argent.text = dollar.toString() +" $"
    }
}