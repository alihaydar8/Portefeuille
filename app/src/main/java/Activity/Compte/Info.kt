package Activity.Compte
import DataBase.MyDBHelper
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_info.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class Info : AppCompatActivity() {

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
        val pieChart2 = findViewById<PieChart>(R.id.piechart2)

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

        var COther2 = 0
        var CSalary = 0
        var CAide = 0
        var CGift = 0
        if (rs2.moveToFirst()) {
            do {
                if (rs2.getString(rs2.getColumnIndex("TA")).toString() == "Other") {
                    COther2 = COther2 + rs2.getString(rs2.getColumnIndex("PA")).toInt()
                }
                if (rs2.getString(rs2.getColumnIndex("TA")).toString() == "Salary") {
                    CSalary = CSalary + rs2.getString(rs2.getColumnIndex("PA")).toInt()
                }
                if (rs2.getString(rs2.getColumnIndex("TA")).toString() == "Aide") {
                    CAide = CAide + rs2.getString(rs2.getColumnIndex("PA")).toInt()
                }
                if (rs2.getString(rs2.getColumnIndex("TA")).toString() == "Gift") {
                    CGift = CGift + rs2.getString(rs2.getColumnIndex("PA")).toInt()
                }
            }
            while (rs2.moveToNext())
        }


        Other2.text = "Other: " + COther2.toString() +" .00 USD"
        Salary.text ="Salary :"+ CSalary.toString()+" .00 USD"
        Aide.text = "Aide :"+ CAide.toString()+" .00 USD"
        Gift.text = "Gift :"+ CGift.toString()+" .00 USD"

        Other1.text = "Other : " + COther1.toString() +" .00 USD"
        Health.text = "Health : " + CHealth.toString() +" .00 USD"
        House.text =  "House : " + CHouse.toString() +" .00 USD"
        Entertainment.text = "Entertainment : " + CEntertainment.toString() +" .00 USD"
        Personel.text = "Personel : " + CPersonel.toString() +" .00 USD"
        Food.text = "Food : " + CFood.toString() +" .00 USD"
        Transport.text = "transport : " + CTransport.toString() +" .00 USD"

    pieChart1.addPieSlice(
        PieModel(
            "Other", COther1.toFloat(),
            Color.parseColor("#FFA726")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Food", CFood.toFloat(),
            Color.parseColor("#66BB6A")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "House", CHouse.toFloat(),
            Color.parseColor("#EF5350")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Transport", CTransport.toFloat(),
            Color.parseColor("#29B6F6")
        )
    )
    pieChart1.addPieSlice(
        PieModel(
            "Entertainment",
                CEntertainment.toFloat(),
            Color.parseColor("#800000")
        )
    )
    pieChart1.addPieSlice(
        PieModel("Personel",
                CPersonel.toFloat(),
            Color.parseColor("#808000")
        )
    )
    pieChart1.addPieSlice(
        PieModel("Health",
                CHealth.toFloat(),
            Color.parseColor("#800080")
        )
    )

    pieChart1!!.startAnimation()


        pieChart2.addPieSlice(
                PieModel(
                        "Other", COther2.toFloat(),
                        Color.parseColor("#FFA726")
                )
        )
        pieChart2.addPieSlice(
                PieModel(
                        "Salary", CSalary.toFloat(),
                        Color.parseColor("#29B6F6")
                )
        )
        pieChart2.addPieSlice(
                PieModel(
                        "Aide", CAide.toFloat(),
                        Color.parseColor("#66BB6A")
                )
        )
        pieChart2.addPieSlice(
                PieModel(
                        "Gift", CGift.toFloat(),
                        Color.parseColor("#EF5350")
                )
        )


        pieChart2!!.startAnimation()
        back5.setOnClickListener { startActivity(Intent(this,compte::class.java)) }
    }
}