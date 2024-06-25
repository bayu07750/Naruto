package com.bayu.narutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: UseCases,
) : ViewModel() {
    val getAllHeroes: Flow<PagingData<Hero>> = useCases.getAllHeroesUseCase().cachedIn(viewModelScope)
}