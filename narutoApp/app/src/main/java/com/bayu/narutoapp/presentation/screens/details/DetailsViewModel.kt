package com.bayu.narutoapp.presentation.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.domain.use_cases.UseCases
import com.bayu.narutoapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var selectedHero by mutableStateOf<Hero?>(null)
        private set

    init {
        viewModelScope.launch {
            val heroId = savedStateHandle.get<Int>(Constants.DETAILS_ARGUMENT_KEY)
            selectedHero = heroId?.let { useCases.getSelectedHeroUseCase(heroId = heroId) }
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    var colorPalette by mutableStateOf<Map<String, String>>(emptyMap())
        private set

    fun updateColorPallete(colors: Map<String, String>) {
        colorPalette = colors
    }
}


sealed interface UiEvent {
    object GenerateColorPalette : UiEvent
}