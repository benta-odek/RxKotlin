package com.odhiambodevelopers.rxkotlin.database

import androidx.room.*

@Dao
interface DetailsDao {
    @Insert
    fun insertDetails(details: DetailsEntity)

    @Delete
    fun deleteDetails(details: DetailsEntity)

    @Query("SELECT * FROM Details_table ORDER BY id ASC")
    fun getAllDetails(): List<DetailsEntity>

    @Update
    fun updateDetails(details: DetailsEntity)
    abstract fun findCheese(s: String): List<DetailsEntity>
}