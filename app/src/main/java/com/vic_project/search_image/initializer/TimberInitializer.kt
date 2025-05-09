package com.vic_project.search_image.initializer

import android.content.Context
import androidx.startup.Initializer
import com.vic_project.search_image.data.local.preferences.initializer.PreferencesInitializer
import com.vic_project.search_image.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer is initialized.")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        PreferencesInitializer::class.java,
    )
}