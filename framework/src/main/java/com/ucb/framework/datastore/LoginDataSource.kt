package com.ucb.framework.datastore
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ucb.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.google.gson.Gson


val Context.dataStore by preferencesDataStore(name = "login_prefs")

class LoginDataSource(private val context: Context) {

    private val USER = stringPreferencesKey("user")
    private var initialized = false

    val gson = Gson()

    suspend fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        context.dataStore.edit { preferences ->
            preferences[USER] = userJson
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER)
        }
    }

    val userFlow: Flow<User?> = context.dataStore.data
        .map { preferences ->
            initialized = true
            preferences[USER]?.let { gson.fromJson(it, User::class.java) }
        }

    fun isInitialized(): Boolean = initialized
}



