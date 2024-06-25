package com.bayu.narutoapp.data.paging_source

import androidx.paging.PagingSource
import com.bayu.narutoapp.data.remote.FakeNarutoApi
import com.bayu.narutoapp.data.remote.NarutoApi
import com.bayu.narutoapp.domain.model.Hero
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchHeroesSourceTest {

    private lateinit var narutoApi: NarutoApi
    private lateinit var heroes: List<Hero>


    @Before
    fun setUp() {
        narutoApi = FakeNarutoApi()
        heroes = FakeNarutoApi.heroes
    }

    @Test
    fun `Search api with existing hero name, expect single hero result, assert LoadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(narutoApi = narutoApi, query = "naruto")
        assertEquals(
            expected = PagingSource.LoadResult.Page<Int, Hero>(
                data = listOf(heroes[1]),
                prevKey = null,
                nextKey = null,
            ),
            actual = heroSource.load(
                params = PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false,
                )
            )
        )
    }

    @Test
    fun `Search api with existing hero name, expect multiple hero result, assert LoadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(narutoApi = narutoApi, query = "to")
        assertEquals(
            expected = PagingSource.LoadResult.Page<Int, Hero>(
                data = heroes.dropLast(1),
                prevKey = null,
                nextKey = null,
            ),
            actual = heroSource.load(
                params = PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false,
                )
            )
        )
    }

    @Test
    fun `Search api with empty hero name, assert empty heroes list and LoadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(narutoApi = narutoApi, query = "")
        val loadResult = heroSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            )
        )

        //val result = narutoApi.searchHeroes("").heroes

        assertEquals(
            expected = PagingSource.LoadResult.Page<Int, Hero>(
                data = emptyList(),
                prevKey = null,
                nextKey = null,
            ),
            actual = loadResult,
        )
        //assertTrue { result.isEmpty() }
        assertTrue { loadResult is PagingSource.LoadResult.Page }
    }

    @Test
    fun `Search api with non existing hero name, assert empty heroes list and LoadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(narutoApi = narutoApi, query = "Unknown")
        val loadResult  = heroSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            )
        )

        //val result = narutoApi.searchHeroes("Unknown").heroes


        assertEquals(
            expected = PagingSource.LoadResult.Page<Int, Hero>(
                data = emptyList(),
                prevKey = null,
                nextKey = null,
            ),
            actual = loadResult,
        )
        //assertTrue { result.isEmpty() }
        assertTrue { loadResult is PagingSource.LoadResult.Page }
    }
}