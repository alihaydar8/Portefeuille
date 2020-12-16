package Activity.Compte

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R


class SignIn : AppCompatActivity() {
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signe_in)

        val Login = findViewById<Button>(R.id.Login)
        val maill = findViewById<TextView>(R.id.maill)
        val passwordl = findViewById<TextView>(R.id.passwordl)


        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
        maill.text = sharedPreferences.getString("mail","")
//Button login
        Login.setOnClickListener {
            if (maill.text.toString() == sharedPreferences.getString("mail","") && passwordl.text.toString() == sharedPreferences.getString("password","") &&
                !maill.text.toString().isNullOrEmpty() && !passwordl.text.toString().isNullOrEmpty() )
            {
                startActivity(Intent(this, compte::class.java))
            }
            else {
                Toast.makeText(this, "mail or pass invalid ",
                    Toast.LENGTH_SHORT).show()
            }
        }


/*
        //      if(nom.text.toString().isNullOrEmpty() && prenom.text.toString().isNullOrEmpty() && mail.text.toString().isNullOrEmpty())
        //    {
        //         \Login.isEnabled
        //     }
*/
    }
}