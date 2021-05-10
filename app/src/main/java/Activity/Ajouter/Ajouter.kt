package Activity.Ajouter

import Activity.Compte.compte
import Class.type
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import DataBase.MyDBHelper
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_ajouter.*
import java.text.SimpleDateFormat
import java.util.*


class Ajouter : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter)

        val helper = MyDBHelper(applicationContext)
        val db2 = helper.readableDatabase
        val n1 = findViewById<TextView>(R.id.n1)
        val back2= findViewById<ImageButton>(R.id.back2)
        val bajouter = findViewById<Button>(R.id.bajouter)
        val ttypee = findViewById<Spinner>(R.id.typee)
        val descriptivee = findViewById<TextView>(R.id.descriptivee)
        val prixx = findViewById<TextView>(R.id.prixx)
        val datee = findViewById<TextView>(R.id.datee)

//SharedPreference (Recuperer)
        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)
        n1.text = sharedPreferences.getString("nom","").toString().toLowerCase() +" "+ sharedPreferences.getString("prenom","").toString().toUpperCase()
        argentA.text = (sharedPreferences.getInt("countA",0) - sharedPreferences.getInt("countD",0)).toString() +".00 USD"

        if (sharedPreferences.getString("Sex","").toString() == "man")
        {
            ImageUserA.setImageResource(R.drawable.man)
        }
        if (sharedPreferences.getString("Sex","").toString() == "woman")
        {
            ImageUserA.setImageResource(R.drawable.woman)
        }
        monthA.text = SimpleDateFormat("dd/MM/YYYY hh:mm").format(Date())
//Spinner Type Ajouter
        val levelList2: MutableList<type> = ArrayList()
        levelList2.add(type("Other"))
        levelList2.add(type("Salary"))
        levelList2.add(type("Aide"))
        levelList2.add(type("Gift"))

        val adapter: ArrayAdapter<type> = ArrayAdapter<type>(this, android.R.layout.simple_spinner_item, levelList2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ttypee.setAdapter(adapter)
        ttypee.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }



  //implementer une calendrier
        val cal2 = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth  ->
            cal2.set(Calendar.YEAR, year)
            cal2.set(Calendar.MONTH, monthOfYear)
            cal2.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            datee.text = java.text.SimpleDateFormat("dd MMMM", Locale.US).format(cal2.time)

        }

        datee.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    cal2.get(Calendar.YEAR),
                    cal2.get(Calendar.MONTH),
                    cal2.get(Calendar.DAY_OF_MONTH)).show()
        }
  //sauvgarder les donnees Database(Ajouter)
        val  rs2 = db2.rawQuery("SELECT * FROM AJOUTER ORDER BY DateA DESC",null)
                bajouter.setOnClickListener {
                    if (!descriptivee.text.toString().isNullOrEmpty() && !prixx.text.toString().isNullOrEmpty()) {
                        val cv2 = ContentValues()
                        cv2.put("TA", typee.selectedItem.toString())
                        cv2.put("DA", descriptivee.text.toString())
                        cv2.put("PA", Integer.parseInt(prixx.text.toString()))
                        if (datee.text.toString().isNullOrEmpty())
                        {cv2.put("DateA", SimpleDateFormat("dd MMMM").format(Date()))}
                        else{cv2.put("DateA",datee.text.toString())}
                        db2.insert("AJOUTER", null, cv2)

                        rs2.requery()
                        val ad = AlertDialog.Builder(this)
                        ad.setTitle("Add Record")
                        ad.setMessage("Record Inserted Successfully")
                        ad.setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->
                            prixx.text = ""
                            descriptivee.text = ""
                            datee.text =""
                            typee.setSelection(0)
                        })
                        ad.show()
                    }
                    else {Toast.makeText(this, "fill the information ", Toast.LENGTH_SHORT).show()}
                }
 //le travail d'Update

        if(sharedPreferences.getString("updateTA","") != "" && sharedPreferences.getString("updatePA","") != "" &&
            sharedPreferences.getString("updateDA","") != "" && sharedPreferences.getString("updateDateA","") != "")
        {
             if (sharedPreferences.getString("updateTA","") == "Other"){ttypee.setSelection(0)}
            if (sharedPreferences.getString("updateTA","") == "Salary"){ttypee.setSelection(1)}
            if (sharedPreferences.getString("updateTA","") == "Aide"){ttypee.setSelection(2)}
            if (sharedPreferences.getString("updateTA","") == "Gift"){ttypee.setSelection(3)}
            descriptivee.text = sharedPreferences.getString("updateDA","")
            prixx.text = sharedPreferences.getString("updatePA","")
            datee.text = sharedPreferences.getString("updateDateA","")
            bajouter.setText("Update")
            bajouter.setOnClickListener {
                if (!descriptivee.text.toString().isNullOrEmpty() && !prixx.text.toString().isNullOrEmpty()) {
                    val cv = ContentValues()
                    cv.put("TA", typee.selectedItem.toString())
                    cv.put("DA", descriptivee.text.toString())
                    cv.put("PA", Integer.parseInt(prixx.text.toString()))
                    if (datee.text.toString().isNullOrEmpty())
                    {cv.put("DateA", SimpleDateFormat("dd MMMM").format(Date()))}
                    else{cv.put("DateA",datee.text.toString())}
                    db2.insert("AJOUTER", null, cv)

                    rs2.requery()
                    val ad = AlertDialog.Builder(this)
                    ad.setTitle("Update Record")
                    ad.setMessage("Record updated Successfully")
                    ad.setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->
                        prixx.text = ""
                        descriptivee.text = ""
                        datee.text =""
                        typee.setSelection(0)
                        val editor = sharedPreferences.edit()
                        editor.putString("updateTA", "")
                        editor.putString("updatePA", "")
                        editor.putString("updateDA", "")
                        editor.putString("updateDateA", "")
                        editor.apply()
                        startActivity(Intent(this,Listeajouter::class.java))
                    })
                    ad.show()
                }
                else {Toast.makeText(this, "fill the information ", Toast.LENGTH_SHORT).show()}
            }
        }



// retourner en arriere
        back2.setOnClickListener {
               startActivity(Intent(this, compte::class.java))

           }
////// Manipuler les etoiles next to the TextView
        descriptivee.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (descriptivee.text.toString().isNullOrEmpty()) {
                    etoileDA.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!descriptivee.text.toString().isNullOrEmpty()) {
                    etoileDA.setTextColor(R.color.vert)
                }
            }
        })

        prixx.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (prixx.text.toString().isNullOrEmpty()) {
                    etoilePA.setTextColor(R.color.red)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!prixx.text.toString().isNullOrEmpty()) {
                    etoilePA.setTextColor(R.color.vert)
                }
            }
        })



    }
}
