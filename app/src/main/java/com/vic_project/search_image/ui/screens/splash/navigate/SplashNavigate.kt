package com.vic_project.search_image.ui.screens.splash.navigate

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vic_project.search_image.ui.screens.splash.SplashScreen
import com.vic_project.search_image.utils.compose.hideNavigationBar
import kotlinx.serialization.Serializable

@Serializable
data object Splash

fun NavController.navigateToSplash(navOptions: NavOptions? = null) = navigate(Splash, navOptions)

fun NavGraphBuilder.splashScreen(
    onNavSearch:() -> Unit,
) {
    composable<Splash> {
        val context = LocalContext.current
        hideNavigationBar(context)
        SplashScreen(
            onNavSearch = onNavSearch
        )
    }
}