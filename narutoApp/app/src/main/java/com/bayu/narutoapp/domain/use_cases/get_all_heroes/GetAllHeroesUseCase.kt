package com.bayu.narutoapp.domain.use_cases.get_all_heroes

import androidx.paging.PagingData
import com.bayu.narutoapp.data.repository.Repository
import com.bayu.narutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository,
) {
    operator fun invoke(): Flow<PagingData<Hero>> = repository.getAllHeroes()
}