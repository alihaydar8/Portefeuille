package Activity.Compte

import Class.Mail
import Class.Sex
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
///////////////////////////J ai appris beaucoup pendant la realisation de cette application
////////////////////////// surtout c etais ma premiere tentatife 
class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences:SharedPreferences
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("Share", Context.MODE_PRIVATE)
        val Sex = findViewById<Spinner>(R.id.Sex)
        val nom = findViewById<TextView>(R.id.nom)
        val prenom = findViewById<TextView>(R.id.prenom)
        val mail = findViewById<TextView>(R.id.mail)
        val password = findViewById<TextView>(R.id.password)
        val Smail = findViewById<Spinner>(R.id.Smail)


  //Spinner sex
        val ageList: MutableList<Sex> = ArrayList()
        ageList.add(Sex("man"))
        ageList.add(Sex("woman"))

        val adapter1: ArrayAdapter<Sex> = ArrayAdapter<Sex>(
            this, android.R.layout.simple_spinner_item, ageList
        )
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Sex.setAdapter(adapter1)
        Sex.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }

   //Spinner Mail
        val mailList: MutableList<Mail> = ArrayList()
        mailList.add(Mail("@gmail.com"))
        mailList.add(Mail("@hotmail.com"))
        mailList.add(Mail("@yahoo.com"))
        mailList.add(Mail("@live.com"))
        mailList.add(Mail("@outlook.com"))

        val adapter2: ArrayAdapter<Mail> =
            ArrayAdapter<Mail>(this, android.R.layout.simple_spinner_item, mailList)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Smail.setAdapter(adapter2)
        Smail.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }
   //LOGIN si il a un autre compte
        Bsignup.setOnClickListener { startActivity(Intent(this, SignIn::class.java)) }

  //Signe (teste que tout les informations sont saisie)
        Bsignim.setOnClickListener {
            if (sharedPreferences.getString(
                    "mail",
                    ""
                ) == "" && sharedPreferences.getString("password", "") == ""
            ) {
                if (Sex.selectedItem.toString() != "Gender" && !password.text.toString()
                        .isNullOrEmpty() && !mail.text.toString().isNullOrEmpty()
                    && !nom.text.toString().isNullOrEmpty() && !prenom.text.toString()
                        .isNullOrEmpty()
                ) {

                    val editor = sharedPreferences.edit()
                    editor.putString("prenom", nom.text.toString())
                    editor.putString("nom", prenom.text.toString())
                    editor.putString("Sex", Sex.selectedItem.toString())
                    editor.putString("password", password.text.toString())
                    editor.putString("mail", mail.text.toString() + Smail.selectedItem.toString())
                    editor.apply()
                    Toast.makeText(
                        this, "accout create successfully ",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, SignIn::class.java))
                } else {
                    Toast.makeText(this, "fill the information ", Toast.LENGTH_SHORT).show()
                }
 ////// cette  partie to be continue (pour cree une obtion )
            } else {
                var oneaccount = AlertDialog.Builder(this)
                oneaccount.setTitle("only one account is possible")
                oneaccount.setMessage("u have to delete the current account then u can create a new account or login in premium")
                oneaccount.setPositiveButton("ok", null)
                oneaccount.show()
            }
        }

////// Manipuler les etoiles next to the TextView
        mail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (mail.text.toString().isNullOrEmpty()) {
                    etoileM.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!mail.text.toString().isNullOrEmpty()) {
                    etoileM.setTextColor(R.color.vert)
                }
            }
        })

        nom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (nom.text.toString().isNullOrEmpty()) {
                    etoileN.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!nom.text.toString().isNullOrEmpty()) {
                    etoileN.setTextColor(R.color.vert)
                }
            }
        })

        prenom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (prenom.text.toString().isNullOrEmpty()) {
                    etoileP.setTextColor(R.color.red)                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!prenom.text.toString().isNullOrEmpty()) {
                    etoileP.setTextColor(R.color.vert)
                }
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (password.text.toString().isNullOrEmpty()) {
                    etoilePass.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!password.text.toString().isNullOrEmpty()) {
                    etoilePass.setTextColor(R.color.vert)
                }
            }
        })

    }
}