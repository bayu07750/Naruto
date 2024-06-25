package com.bayu.narutoapp.domain.repository

import com.bayu.narutoapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}