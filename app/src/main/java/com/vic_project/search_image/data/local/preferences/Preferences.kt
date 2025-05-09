package com.vic_project.search_image.data.local.preferences

import android.content.SharedPreferences
import com.vic_project.search_image.data.local.preferences.delegate.booleanPreferences
import com.vic_project.search_image.data.local.preferences.delegate.stringPreferences
import javax.inject.Inject

class Preferences @Inject constructor(
    val sharedPreferences: SharedPreferences
) {

    var listSearch by stringPreferences(
        key = CURRENT_USER,
        defaultValue = ""
    )

    companion object {
        private const val CURRENT_USER: String = "key_list_search"
    }
}

val String.Companion.Empty
    inline get() = ""

val Boolean.Companion.BolDefault
    inline get() = false
