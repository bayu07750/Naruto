package com.bayu.narutoapp.domain.use_cases.save_onboarding

import com.bayu.narutoapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed)
    }
}