package com.example.myapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="depence")
data class depence(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var type:String,
    var descriptive: String,
    var prix : Int
){
    constructor(type: String, descriptive :String,prix:Int) : this(0, type, descriptive, prix)
}