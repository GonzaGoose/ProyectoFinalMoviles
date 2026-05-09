package com.example.projectofinalmoviles.componentes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "medicamentos")

class DataStoreManager(private val context: Context) {

    companion object {
        private val MEDICAMENTOS_KEY = stringPreferencesKey("medicamentos_list")
    }


    suspend fun guardarMedicamentos(medicamentos: List<Medicamento>) {
        try {
            val json = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                prettyPrint = false
            }
            val medicamentosJson = json.encodeToString(medicamentos)
            context.dataStore.edit { preferences ->
                preferences[MEDICAMENTOS_KEY] = medicamentosJson
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun cargarMedicamentos(): Flow<List<Medicamento>> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[MEDICAMENTOS_KEY]
            if (jsonString.isNullOrEmpty()) {
                return@map emptyList()
            }
            try {
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                json.decodeFromString<List<Medicamento>>(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}