package com.bayu.narutoapp.domain.use_cases.search_heroes

import com.bayu.narutoapp.data.repository.Repository

class SearchHeroesUseCase(
    private val repository: Repository
) {

    operator fun invoke(query: String) = repository.searchHeroes(query)
}