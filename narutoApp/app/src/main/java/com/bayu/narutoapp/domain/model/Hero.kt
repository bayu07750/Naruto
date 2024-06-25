package com.bayu.narutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bayu.narutoapp.util.Constants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Constants.HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>,
)
