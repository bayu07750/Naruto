package com.bayu.narutoapp.domain.repository

import kotlinx.coroutines.flow.Flow


interface DataStoreOperations {
    suspend fun saveOnBoardingState(value: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}