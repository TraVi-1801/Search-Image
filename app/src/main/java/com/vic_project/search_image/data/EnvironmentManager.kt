package com.vic_project.search_image.data

import com.vic_project.search_image.utils.AppConstants
import com.vic_project.search_image.utils.time.TimeUtils.toDate

object EnvironmentManager {
    var baseUrl : BuildEnvironment = BuildEnvironment.DOMAIN_DEV
    fun setDomain(buildEnvironment: BuildEnvironment) {
        baseUrl = buildEnvironment
    }

    val versionApp = "App \u00A9 ${
        System.currentTimeMillis().toDate("yyyy")
    } \u25CF ${baseUrl.environmentName}_${AppConstants.VERSION_NAME}"
}

enum class BuildEnvironment(
    val url_v1: String,
    val url_v3: String,
    val loadData: String,
    val position: Int,
    val environmentName : String,
) {
    DOMAIN_DEV(
        "https://api.pexels.com/v1/",
        "https://www.pexels.com/en-us/api/v3/",
        "",
        0,
        environmentName = "Dev",
    ),
    DOMAIN_STAGING(
        "",
        "",
        "",
        1,
        environmentName = "Staging",
    ),
    DOMAIN_LIVE(
        "",
        "",
        "",
        2,
        environmentName = "Live",
    );

    companion object {
        fun filter(position: Int): BuildEnvironment {
            for (environment in values()) {
                if (position == environment.position) {
                    return environment
                }
            }
            return DOMAIN_DEV
        }
    }
}