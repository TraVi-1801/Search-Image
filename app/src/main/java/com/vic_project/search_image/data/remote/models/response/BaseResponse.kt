package com.vic_project.search_image.data.remote.models.response

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.util.Objects

open class BaseResponse {
    @SerializedName("data")
    val data: JsonElement? = null
    @SerializedName("photos")
    val photos: JsonElement? = null
    @SerializedName("messages")
    val message: String = ""
    @SerializedName("code")
    val code: String? = null

    fun data(): String {
        return Objects.toString(data?:photos)
    }

    val dataObject: JsonObject
        get() = (data?:photos)!!.asJsonObject

    val dataArray: JsonArray
        get() = (data?:photos)!!.asJsonArray

}