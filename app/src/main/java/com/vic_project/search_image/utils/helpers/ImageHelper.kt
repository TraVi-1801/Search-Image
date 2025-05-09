package com.vic_project.search_image.utils.helpers

import com.vic_project.search_image.data.EnvironmentManager

object ImageHelper {

    fun String?.toImage() : String {
        if (this.isNullOrBlank()) return ""
        return EnvironmentManager.baseUrl.loadData + this
    }


}