package com.bayu.narutoapp.di

import android.content.Context
import com.bayu.narutoapp.data.local.NarutoDatabase
import com.bayu.narutoapp.data.remote.NarutoApi
import com.bayu.narutoapp.data.repository.DataStoreOperationsImpl
import com.bayu.narutoapp.data.repository.LocalDataSourceImpl
import com.bayu.narutoapp.data.repository.RemoteDataSourceImpl
import com.bayu.narutoapp.data.repository.Repository
import com.bayu.narutoapp.domain.repository.DataStoreOperations
import com.bayu.narutoapp.domain.repository.LocalDataSource
import com.bayu.narutoapp.domain.repository.RemoteDataSource
import com.bayu.narutoapp.domain.use_cases.UseCases
import com.bayu.narutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.bayu.narutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.bayu.narutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.bayu.narutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.bayu.narutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationsImpl(context)
    }

    @Singleton
    @Provides
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository),
        )
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(narutoApi: NarutoApi, narutoDatabase: NarutoDatabase): RemoteDataSource {
        return RemoteDataSourceImpl(narutoApi, narutoDatabase)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(narutoDatabase: NarutoDatabase): LocalDataSource = LocalDataSourceImpl(narutoDatabase)
}