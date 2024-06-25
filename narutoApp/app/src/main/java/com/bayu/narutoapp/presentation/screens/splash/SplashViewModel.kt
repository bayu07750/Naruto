package com.bayu.narutoapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayu.narutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val _onboardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted = _onboardingCompleted.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onboardingCompleted.value = useCases.readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }
}