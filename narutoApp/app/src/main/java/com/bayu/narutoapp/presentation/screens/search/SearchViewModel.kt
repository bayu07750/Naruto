package com.bayu.narutoapp.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    private val _searchHeroes = MutableStateFlow<PagingData<Hero>>(PagingData.from(emptyList()))
    val searchHeroes = _searchHeroes.asStateFlow()

    fun searchHeroes(query: String) {
        viewModelScope.launch {
            useCases.searchHeroesUseCase(query).cachedIn(viewModelScope).collect {
                _searchHeroes.value = it
            }
        }
    }
}