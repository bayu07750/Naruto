package com.bayu.narutoapp.domain.use_cases.read_onboarding

import com.bayu.narutoapp.data.repository.Repository

class ReadOnBoardingUseCase (
    private val repository: Repository
) {

    operator fun invoke() = repository.readOnBoardingState()
}