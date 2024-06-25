package com.bayu.narutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bayu.narutoapp.data.local.NarutoDatabase
import com.bayu.narutoapp.data.paging_source.HeroRemoteMediator
import com.bayu.narutoapp.data.paging_source.SearchHeroesSource
import com.bayu.narutoapp.data.remote.NarutoApi
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.repository.RemoteDataSource
import com.bayu.narutoapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val narutoApi: NarutoApi,
    private val narutoDatabase: NarutoDatabase,
) : RemoteDataSource {

    private val heroData = narutoDatabase.heroDao()
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory  = { heroData.getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                narutoApi, narutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = 4),
            pagingSourceFactory = {
                SearchHeroesSource(narutoApi, query)
            }
        ).flow
    }
}