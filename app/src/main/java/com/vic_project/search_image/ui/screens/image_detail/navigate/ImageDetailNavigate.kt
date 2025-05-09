package com.vic_project.search_image.ui.screens.image_detail.navigate

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vic_project.search_image.ui.models.ImageModel
import com.vic_project.search_image.ui.screens.image_detail.ImageDetailScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class Details(val image: ImageModel) {
    companion object {
        val typeMap = mapOf(typeOf<ImageModel>() to ImageDetailType)
    }
}

fun NavController.navigateToSearchImage(navOptions: NavOptions? = null, data: ImageModel) =
    navigate(Details(data), navOptions)

fun NavGraphBuilder.imageDetailScreen(
    onBack: () -> Unit
) {
    composable<Details>(
        typeMap = Details.typeMap,
    ) {
        ImageDetailScreen(
            onBack = onBack
        )
    }
}