package com.bayu.narutoapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.bayu.narutoapp.domain.repository.DataStoreOperations
import com.bayu.narutoapp.util.Constants.ONBOARDING_KEY
import com.bayu.narutoapp.util.Constants.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(
    @ApplicationContext context: Context,
) : DataStoreOperations {

    private object PreferenceKey {
        val onBoardingKey = booleanPreferencesKey(ONBOARDING_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.onBoardingKey] = value
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preference ->
                preference[PreferenceKey.onBoardingKey] ?: false
            }
    }
}