package com.vic_project.search_image.domain.models

import com.vic_project.search_image.BuildConfig

object AuthModel {

    fun headerWithAPIKey(): HashMap<String, String> {
        val headers: HashMap<String, String> = HashMap()
        headers["Authorization"] = BuildConfig.API_KEY
        return headers
    }
    fun headerWithContentType(): HashMap<String, String> {
        val headers: HashMap<String, String> = HashMap()
        return headers
    }
}