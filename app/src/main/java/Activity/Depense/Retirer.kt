package Activity.Depense

import Activity.Compte.*
import Class.type
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import DataBase.MyDBHelper
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_ajouter.*

import kotlinx.android.synthetic.main.activity_retirer.*
import java.text.SimpleDateFormat
import java.util.*

class Retirer : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retirer)

        var helper = MyDBHelper(applicationContext)
        var db1 = helper.readableDatabase

        val n2 = findViewById<TextView>(R.id.n2)
        val back1= findViewById<ImageButton>(R.id.back1)
        val Bretirer = findViewById<Button>(R.id.Bretirer)
        val Type = findViewById<Spinner>(R.id.type)
        val descriptive = findViewById<TextView>(R.id.descriptive)
        val prix = findViewById<TextView>(R.id.prix)
        val date = findViewById<TextView>(R.id.date)
//SharedPreference (Recuperer)
        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
        n2.text = sharedPreferences.getString("nom","").toString().toLowerCase() +" "+ sharedPreferences.getString("prenom","").toString().toUpperCase()
        argentD.text = (sharedPreferences.getInt("countA",0) - sharedPreferences.getInt("countD",0)).toString() +".00 USD"

        if (sharedPreferences.getString("Sex","").toString() == "man")
        {
            ImageUserD.setImageResource(R.drawable.man)
        }
        if (sharedPreferences.getString("Sex","").toString() == "woman")
        {
            ImageUserD.setImageResource(R.drawable.woman)
        }
        monthD.text = SimpleDateFormat("dd/MMMM/YYYY hh:mm").format(Date())
//Spinner Type Depense
        val levelList1: MutableList<type> = ArrayList()
        levelList1.add(type("Other"))
        levelList1.add(type("Food"))
        levelList1.add(type("House"))
        levelList1.add(type("Transport"))
        levelList1.add(type("Entertainment"))
        levelList1.add(type("Personnel"))
        levelList1.add(type("Health"))

        val adapter: ArrayAdapter<type> = ArrayAdapter<type>(
                this, android.R.layout.simple_spinner_item, levelList1
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Type.setAdapter(adapter)
        Type.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }



 //implementer une calendrier
        var cal1 = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth  ->
            cal1.set(Calendar.YEAR, year)
            cal1.set(Calendar.MONTH, monthOfYear)
            cal1.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            date.text = SimpleDateFormat("dd MMMM", Locale.US).format(cal1.time)
        }
        date.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    cal1.get(Calendar.YEAR),
                    cal1.get(Calendar.MONTH),
                    cal1.get(Calendar.DAY_OF_MONTH)).show()
        }

 //sauvgarder les donnees Database(DPENSE)
        var  rs1 = db1.rawQuery("SELECT * FROM DEPENSE ORDER BY DateD DESC ",null)
        Bretirer.setOnClickListener {
            if (!descriptive.text.toString().isNullOrEmpty() && !prix.text.toString().isNullOrEmpty()) {
                var cv1 = ContentValues()
                cv1.put("TD", type.selectedItem.toString())
                cv1.put("DD", descriptive.text.toString())
                cv1.put("PD",prix.text.toString().toInt())
                if (date.text.toString().isNullOrEmpty())
                { cv1.put("DateD", SimpleDateFormat(" dd MMMM").format(Date()))}
                else{
                    cv1.put("DateD",date.text.toString())
                }
                db1.insert("DEPENSE", null, cv1)

                rs1.requery()
                var ad = AlertDialog.Builder(this)
                ad.setTitle("Add Record")
                ad.setMessage("Record Inserted Successfully")
                ad.setPositiveButton("ok", DialogInterface.OnClickListener {_,_ ->
                    prix.text = ""
                    descriptive.text = ""
                    date.text=""
                    type.setSelection(0)
                })
                ad.show()
            }
            else {Toast.makeText(this, "fill the information ", Toast.LENGTH_SHORT).show()}
        }
 //le travail d'Update

        if(sharedPreferences.getString("updateTD","") != "" && sharedPreferences.getString("updatePD","") != "" &&
            sharedPreferences.getString("updateDD","") != "" && sharedPreferences.getString("updateDateD","") != "")
        {
            if (sharedPreferences.getString("updateTD","") == "Other"){Type.setSelection(0)}
            if (sharedPreferences.getString("updateTD","") == "Food"){Type.setSelection(1)}
            if (sharedPreferences.getString("updateTD","") == "House"){Type.setSelection(2)}
            if (sharedPreferences.getString("updateTD","") == "transport"){Type.setSelection(3)}
            if (sharedPreferences.getString("updateTD","") == "entertainment"){Type.setSelection(4)}
            if (sharedPreferences.getString("updateTD","") == "Personnel"){Type.setSelection(5)}
            if (sharedPreferences.getString("updateTD","") == "Health"){Type.setSelection(6)}
            descriptive.text = sharedPreferences.getString("updateDD","")
            prix.text = sharedPreferences.getString("updatePD","")
            date.text = sharedPreferences.getString("updateDateD","")
            Bretirer.setText("Update")
            Bretirer.setOnClickListener {
                if (!descriptive.text.toString().isNullOrEmpty() && !prix.text.toString().isNullOrEmpty()) {
                    var cv = ContentValues()
                    cv.put("TD", "type:" + type.selectedItem.toString())
                    cv.put("DD", descriptive.text.toString())
                    cv.put("PD",prix.text.toString().toInt())
                    if (date.text.toString().isNullOrEmpty())
                    { cv.put("DateD", SimpleDateFormat(" dd MMMM").format(Date()))}
                    else{
                        cv.put("DateD",date.text.toString())
                    }
                    db1.insert("DEPENSE", null, cv)

                    rs1.requery()
                    var ad = AlertDialog.Builder(this)
                    ad.setTitle("UPDATE Record")
                    ad.setMessage("Record updated Successfully")
                    ad.setPositiveButton("ok", DialogInterface.OnClickListener {_,_ ->
                        prix.text = ""
                        descriptive.text = ""
                        date.text=""
                        type.setSelection(0)
                        val editor = sharedPreferences.edit()
                        editor.putString("updateTD", "")
                        editor.putString("updatePD", "")
                        editor.putString("updateDD", "")
                        editor.putString("updateDateD", "")
                        editor.apply()
                        startActivity(Intent(this, Listeretirer::class.java))
                    })
                    ad.show()
                }
                else {Toast.makeText(this, "fill the information ", Toast.LENGTH_SHORT).show()}
            }
        }




        //retourner en arriere
        back1.setOnClickListener {
            startActivity(Intent(this, compte::class.java))
        }
   //// Manipuler les etoiles next to the TextView
        descriptive.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (descriptive.text.toString().isNullOrEmpty()) {
                    etoileDD.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!descriptive.text.toString().isNullOrEmpty()) {
                    etoileDD.setTextColor(R.color.vert)
                }
            }
        })

        prix.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (prix.text.toString().isNullOrEmpty()) {
                    etoilePD.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!prix.text.toString().isNullOrEmpty()) {
                    etoilePD.setTextColor(R.color.vert)
                }
            }
        })
    }
}

