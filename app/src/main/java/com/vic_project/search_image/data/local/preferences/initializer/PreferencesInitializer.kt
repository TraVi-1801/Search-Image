package com.vic_project.search_image.data.local.preferences.initializer

import android.content.Context
import androidx.startup.Initializer
import com.vic_project.search_image.data.local.preferences.Preferences
import com.vic_project.search_image.di.preferences.PreferencesEntryPoint
import javax.inject.Inject

class PreferencesInitializer : Initializer<Unit> {

    @set:Inject
    internal lateinit var preferences: Preferences

    override fun create(context: Context) {
        PreferencesEntryPoint.resolve(context).inject(this)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf()
}