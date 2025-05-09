package com.vic_project.search_image.ui.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Immutable
@Parcelize
@Serializable
data class ImageModel(
    val id: String,
    val photographer: String? = null,
    val url: String? = null,
    val urlSmall: String? = null,
    val description: String? = null
) : Parcelable
