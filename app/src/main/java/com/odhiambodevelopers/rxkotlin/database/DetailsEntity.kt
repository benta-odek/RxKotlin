package com.odhiambodevelopers.rxkotlin.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Details_table")
data class DetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val weight: String? = null
)
