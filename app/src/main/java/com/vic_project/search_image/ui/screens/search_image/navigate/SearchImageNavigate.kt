package com.vic_project.search_image.ui.screens.search_image.navigate

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vic_project.search_image.ui.models.ImageModel
import com.vic_project.search_image.ui.screens.search_image.SearchImageScreen
import com.vic_project.search_image.utils.compose.showNavigationBar
import kotlinx.serialization.Serializable

@Serializable
data object Home

fun NavController.navigateToSearchImage(navOptions: NavOptions? = null) =
    navigate(Home, navOptions)

fun NavGraphBuilder.searchImageScreen(
    onNavToImageDetail: (ImageModel) -> Unit,
) {
    composable<Home> {
        val context = LocalContext.current
        showNavigationBar(context)
        SearchImageScreen(
            onNavToImageDetail = onNavToImageDetail
        )
    }
}