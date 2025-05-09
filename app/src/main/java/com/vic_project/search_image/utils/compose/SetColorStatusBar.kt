package com.vic_project.search_image.utils.compose

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@Composable
fun SetColorStatusBar(
    color: Color,
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
}
@Composable
fun SetColorNavigateBar(
    color: Color,
) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.navigationBarColor = color.toArgb()
    }
}

fun hideNavigationBar(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        (context as Activity).window.setDecorFitsSystemWindows(false)
        (context as Activity).window.insetsController?.hide(WindowInsets.Type.navigationBars())
    } else {
        (context as Activity).window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}

fun showNavigationBar(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val window = (context as Activity).window
        window.setDecorFitsSystemWindows(false) // Giữ nội dung tràn màn hình
        window.insetsController?.apply {
            show(WindowInsets.Type.navigationBars()) // Hiển thị navigation bar
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        val window = (context as Activity).window
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
    }
}