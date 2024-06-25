package com.bayu.narutoapp.data.repository

import com.bayu.narutoapp.data.local.NarutoDatabase
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.repository.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    narutoDatabase: NarutoDatabase,
) : LocalDataSource {

    private val heroDao = narutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return withContext(Dispatchers.IO) {
            heroDao.getSelectedHero(heroId)
        }
    }
}