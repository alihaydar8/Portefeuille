package com.example.myapplication.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName="addition")
data class addition(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var type:String,
    var descriptive: String,
    var prix : Int
){
    constructor(type: String, descriptive :String, prix:Int) : this (0, type, descriptive, 0)
}