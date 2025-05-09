package com.vic_project.search_image

import android.app.Application
import com.vic_project.search_image.utils.AppConstants
import com.vic_project.search_image.utils.LogUtils
import com.vic_project.search_image.utils.helpers.HashPasswordHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        HashPasswordHelper.getByte(assets)
        if (BuildConfig.DEBUG) {
            LogUtils.plantDebugTree()
        }
        AppConstants.VERSION_NAME = BuildConfig.VERSION_NAME
    }
}