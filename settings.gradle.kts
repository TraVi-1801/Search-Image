pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = "https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1")
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("lib") {
            from(files("./gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "SearchImage"
include(":app")