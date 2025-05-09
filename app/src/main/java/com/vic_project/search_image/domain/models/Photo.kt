package com.vic_project.search_image.domain.models

import com.vic_project.search_image.ui.models.ImageModel
import com.vic_project.search_image.utils.extensions.StringExtension

data class Photo(
    val alt: String? = null,
    val avg_color: String? = null,
    val height: Int? = null,
    val id: Long? = null,
    val liked: Boolean? = null,
    val photographer: String? = null,
    val photographer_id: Long? = null,
    val photographer_url: String? = null,
    val src: Src? = null,
    val url: String? = null,
    val width: Int? = null
){
    fun convertImageData() : ImageModel {
        return ImageModel(
            id = StringExtension.randomUUID(),
            photographer = photographer.orEmpty(),
            url = src?.original.orEmpty(),
            description = alt.orEmpty(),
            urlSmall = src?.medium.orEmpty()
        )
    }
}