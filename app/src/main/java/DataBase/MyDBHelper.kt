package DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context,"DB",null,1)
{
    override fun onCreate(db1: SQLiteDatabase?) {
        db1?.execSQL("CREATE TABLE AJOUTER (_id INTEGER PRIMARY KEY AUTOINCREMENT,TA TEXT,DA TEXT,PA INTEGER ,DateA DATE) ")
        db1?.execSQL("CREATE TABLE DEPENSE (_id INTEGER PRIMARY KEY AUTOINCREMENT,TD TEXT,DD TEXT,PD INTEGER ,DateD DATE) ")
            }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}
