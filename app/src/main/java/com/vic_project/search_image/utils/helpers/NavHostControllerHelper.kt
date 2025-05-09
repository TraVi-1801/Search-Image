package com.vic_project.search_image.utils.helpers

import androidx.navigation.NavHostController

fun <T> NavHostController.setData(
    key: String, value: T?
) {
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> NavHostController.getData(key: String): T? {
    return previousBackStackEntry?.savedStateHandle?.get<T>(
        key
    )
}

fun <T> NavHostController.setPrevData(
    key: String,
    value: T?
) {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)
}

fun <T> NavHostController.getPrevData(key: String): T? {
    return currentBackStackEntry?.savedStateHandle?.get<T>(
        key
    )
}

fun <T> NavHostController.removeKey(
    key: String
) {
    currentBackStackEntry?.savedStateHandle?.remove<T>(key)
}

fun <T> NavHostController.removePrevKey(
    key: String
) {
    previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}