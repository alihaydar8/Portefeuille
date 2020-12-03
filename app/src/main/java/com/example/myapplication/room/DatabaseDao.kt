package com.example.myapplication.room

import androidx.room.*

@Dao
interface DatabaseDao {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun insertdepemce(depence: depence)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun insertaddition(addition: addition)






    @Query("SELECT * FROM depence")
    fun getAlldepence(): List<depence>

    @Query("SELECT * FROM addition")
    fun getAlladdition(): List<depence>


    @Query("DELETE FROM depence WHERE id LIKE :playerId")
    fun deletedepenceFromId(playerId: Long)

    @Query("DELETE FROM addition WHERE id LIKE :playerId")
    fun deleteadditionFromId(playerId: Long)

    @Query("DELETE FROM depence")
    fun deleteAlldepence()

    @Query("DELETE FROM addition")
    fun deleteAlladdition()
}