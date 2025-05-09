package com.vic_project.search_image.data.local.preferences.delegate

import androidx.core.content.edit
import com.vic_project.search_image.data.local.preferences.Preferences
import kotlin.reflect.KProperty

fun booleanPreferences(
    key: String,
    defaultValue: Boolean
) = BooleanPreferenceDelegate(key, defaultValue)

class BooleanPreferenceDelegate(
    private val key: String,
    private val defaultValue: Boolean
) {
    operator fun getValue(preferences: Preferences, property: KProperty<*>): Boolean {
        return preferences.sharedPreferences.getBoolean(key, defaultValue)
    }

    operator fun setValue(preferences: Preferences, property: KProperty<*>, value: Boolean?) {
        if (value != null) {
            preferences.sharedPreferences.edit {
                putBoolean(key, value)
            }
        } else {
            preferences.sharedPreferences.edit {
                remove(key)
            }
        }
    }
}
