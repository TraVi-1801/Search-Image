package com.vic_project.search_image.ui.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.vic_project.search_image.ui.AppState
import com.vic_project.search_image.ui.screens.image_detail.navigate.imageDetailScreen
import com.vic_project.search_image.ui.screens.image_detail.navigate.navigateToSearchImage
import com.vic_project.search_image.ui.screens.search_image.navigate.Home
import com.vic_project.search_image.ui.screens.search_image.navigate.searchImageScreen
import com.vic_project.search_image.ui.screens.splash.navigate.Splash
import com.vic_project.search_image.ui.screens.splash.navigate.splashScreen

@Composable
fun AppNavHost(
    appState: AppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    val navToSearch = {
        navController.navigate(Home) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier,
    ) {
        splashScreen(
            onNavSearch = navToSearch
        )
        searchImageScreen(
            onNavToImageDetail = {
                navController.navigateToSearchImage(data = it)
            }
        )
        imageDetailScreen(
            onBack = { navController.popBackStack() }
        )
    }
}