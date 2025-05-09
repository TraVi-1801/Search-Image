package com.vic_project.search_image.ui.screens.image_detail.navigate

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.CollectionNavType
import androidx.navigation.NavType
import com.vic_project.search_image.ui.models.ImageModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ImageDetailType : NavType<ImageModel>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): ImageModel? =
        BundleCompat.getParcelable(bundle, key, ImageModel::class.java)

    override fun parseValue(value: String): ImageModel {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: ImageModel
    ) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: ImageModel): String =
        Uri.encode(Json.encodeToString(value))
}