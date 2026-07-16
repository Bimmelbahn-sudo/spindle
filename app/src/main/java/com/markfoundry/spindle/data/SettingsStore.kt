package com.markfoundry.spindle.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "spindle_settings")

/** Persists user preferences. Currently: the chosen skin. */
class SettingsStore(private val context: Context) {

    private val skinKey = stringPreferencesKey("skin_id")

    /** Emits the saved skin id, or null if the user hasn't chosen one yet. */
    val skinId: Flow<String?> = context.dataStore.data.map { it[skinKey] }

    suspend fun setSkinId(id: String) {
        context.dataStore.edit { it[skinKey] = id }
    }
}
