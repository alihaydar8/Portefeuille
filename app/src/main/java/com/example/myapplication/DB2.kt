package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB2(context: Context) : SQLiteOpenHelper(context,"DEPENSE",null,1)
{
    override fun onCreate(db2: SQLiteDatabase?) {
        db2?.execSQL("CREATE TABLE DEPENSE (USERIDD INTEGER PRIMARY KEY AUTOINCREMENT,TD TEXT,DD TEXT,PD INTEGER )")



    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}
