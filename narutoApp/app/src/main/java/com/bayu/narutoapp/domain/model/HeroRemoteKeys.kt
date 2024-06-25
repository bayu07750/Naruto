package com.bayu.narutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bayu.narutoapp.util.Constants

@Entity(tableName = Constants.HERO_REMOTE_KEYS_DATABASE_TABLE)
data class HeroRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)