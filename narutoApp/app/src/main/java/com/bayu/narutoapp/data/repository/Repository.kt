package com.bayu.narutoapp.data.repository

import androidx.paging.PagingData
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.repository.DataStoreOperations
import com.bayu.narutoapp.domain.repository.LocalDataSource
import com.bayu.narutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreOperations,
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) {

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

    fun getAllHeroes(): Flow<PagingData<Hero>> = remote.getAllHeroes()

    fun searchHeroes(query: String) = remote.searchHeroes(query)

    suspend fun getSelectedHero(heroId: Int) = local.getSelectedHero(heroId)
}