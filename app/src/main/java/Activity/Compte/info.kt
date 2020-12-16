package Activity.Compte
import DataBase.MyDBHelper
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.R
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_retirer.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class info : AppCompatActivity() {

    lateinit var db1: SQLiteDatabase
    lateinit var db2: SQLiteDatabase
    lateinit var rs1: Cursor
    lateinit var rs2: Cursor
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        sharedPreferences = getSharedPreferences("Share",Context.MODE_PRIVATE)

        val helper = MyDBHelper(applicationContext)
        db1 = helper.writableDatabase
        db2 = helper.writableDatabase
        val pieChart1 = findViewById<PieChart>(R.id.piechart1)
        val pieChart2 = findViewById<PieChart>(R.id.piechart1)

        val Other1 = findViewById<TextView>(R.id.Other1)
        val Health = findViewById<TextView>(R.id.Health)
        val House = findViewById<TextView>(R.id.House)
        val Entertainment = findViewById<TextView>(R.id.Entertainment)
        val Personel = findViewById<TextView>(R.id.Personel)
        val Food = findViewById<TextView>(R.id.Food)
        val Transport = findViewById<TextView>(R.id.Transport)

        val Other2 = findViewById<TextView>(R.id.Other2)
        val Salary = findViewById<TextView>(R.id.Salary)
        val Aide = findViewById<TextView>(R.id.Aide)
        val Gift = findViewById<TextView>(R.id.Gift)


        n5.text = sharedPreferences.getString("nom","").toString().toLowerCase() +" "+ sharedPreferences.getString("prenom","").toString().toUpperCase()
        argentI.text = (sharedPreferences.getInt("countA",0) - sharedPreferences.getInt("countD",0)).toString() +".00 USD"

        if (sharedPreferences.getString("Sex","").toString() == "man")
        {
            ImageUserI.setImageResource(R.drawable.man)
        }
        if (sharedPreferences.getString("Sex","").toString() == "woman")
        {
            ImageUserI.setImageResource(R.drawable.woman)
        }


        rs1 =db1.rawQuery("SELECT * FROM DEPENSE ", null)
        rs2 =db2.rawQuery("SELECT * FROM AJOUTER ", null)
        var COther1= 0
        var CHealth = 0
        var CHouse = 0
        var CEntertainment = 0
        var CPersonel = 0
        var CFood = 0
        var CTransport = 0
        if (rs1.moveToFirst()) {
            do {
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "Other") {
                    COther1 = COther1 + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "Health") {
                    CHealth = CHealth + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "House") {
                    CHouse = CHouse + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "Entertainment") {
                    CEntertainment = CEntertainment + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "Personel") {
                    CPersonel = CPersonel + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "Food") {
                    CFood = CFood + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }
                if (rs1.getString(rs1.getColumnIndex("TD")).toString() == "Transport") {
                    CTransport = CTransport + rs1.getString(rs1.getColumnIndex("PD")).toInt()
                }

            }
            while (rs1.moveToNext())
        }




        Other2.text = "Other: "
        Salary.text ="Salary"
        Aide.text = "Aide"
        Gift.text = "Gift"

        Other1.text = "Other : " + COther1.toString() +" .00 EURO"
        Health.text = "Health : " + CHealth.toString() +" .00 EURO"
        House.text =  "House : " + CHouse.toString() +" .00 EURO"
        Entertainment.text = "Entertainment : " + CEntertainment.toString() +" .00 EURO"
        Personel.text = "Personel : " + CPersonel.toString() +" .00 EURO"
        Food.text = "Food : " + CFood.toString() +" .00 EURO"
        Transport.text = "transport : " + CTransport.toString() +" .00 EURO"



if(sharedPreferences.getInt("countD",0) != 0) {
    pieChart1.addPieSlice(
        PieModel(
            "Other", ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'Other' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.parseColor("#FFA726")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Food", ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'Food' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.parseColor("#66BB6A")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "House", ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'House' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.parseColor("#EF5350")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Transport", ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'Transport' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.parseColor("#29B6F6")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Entertainment",
            ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'Entertainment' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.parseColor("#365993")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Personnel", ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'Personnel' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.BLUE
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Health", ((db1.rawQuery("SELECT SUM(PD) FROM DEPENSE WHERE TD = 'Health' ", null)
                .getColumnIndex("PD") * 100) / sharedPreferences.getInt("countD", 0)).toFloat(),
            Color.GRAY
        )
    )

    pieChart1!!.startAnimation()
}






        pieChart2!!.startAnimation()
        back5.setOnClickListener { startActivity(Intent(this,compte::class.java)) }
    }
}