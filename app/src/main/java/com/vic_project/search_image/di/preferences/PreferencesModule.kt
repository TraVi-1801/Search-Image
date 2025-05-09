package com.vic_project.search_image.di.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.vic_project.search_image.data.local.preferences.Preferences
import com.vic_project.search_image.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): Preferences {
        val sharedPreferences =
            context.getSharedPreferences(AppConstants.App_Preferences, MODE_PRIVATE)
        return Preferences(sharedPreferences)
    }
}
