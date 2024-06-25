package com.bayu.narutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.core.app.ApplicationProvider
import com.bayu.narutoapp.data.local.NarutoDatabase
import com.bayu.narutoapp.data.remote.FakeNarutoApi2
import com.bayu.narutoapp.data.remote.NarutoApi
import com.bayu.narutoapp.domain.model.Hero
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediatorTest {

    private lateinit var narutoApi: NarutoApi
    private lateinit var narutoDatabase: NarutoDatabase

    @Before
    fun setUp() {
        narutoApi = FakeNarutoApi2()
        narutoDatabase = NarutoDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true,
        )
    }

    @After
    fun tearDown() {
        narutoDatabase.clearAllTables()
    }

    @Test
    fun resultLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val heroRemoteMediator = HeroRemoteMediator(narutoApi, narutoDatabase)
        val pagingState = PagingState<Int, Hero>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )
        val result = heroRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as? RemoteMediator.MediatorResult.Success)?.endOfPaginationReached == true)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() = runTest {
        (narutoApi as FakeNarutoApi2).clearData()
        val heroRemoteMediator = HeroRemoteMediator(narutoApi, narutoDatabase)
        val pagingState = PagingState<Int, Hero>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )
        val result = heroRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as? RemoteMediator.MediatorResult.Success)?.endOfPaginationReached == true)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        (narutoApi as FakeNarutoApi2).addException()
        val heroRemoteMediator = HeroRemoteMediator(narutoApi, narutoDatabase)
        val pagingState = PagingState<Int, Hero>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )
        val result = heroRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}