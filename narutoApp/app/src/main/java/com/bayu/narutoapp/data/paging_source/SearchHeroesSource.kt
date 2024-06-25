package com.bayu.narutoapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bayu.narutoapp.data.remote.NarutoApi
import com.bayu.narutoapp.domain.model.Hero

class SearchHeroesSource(
    private val narutoApi: NarutoApi,
    private val query: String,
): PagingSource<Int, Hero>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        return try {
            val apiResponse = narutoApi.searchHeroes(name = query)
            val heroes = apiResponse.heroes
            if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage,
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition
    }

}