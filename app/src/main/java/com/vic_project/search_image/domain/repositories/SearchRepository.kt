package com.vic_project.search_image.domain.repositories

import com.vic_project.search_image.data.remote.models.response.BaseResponse
import com.vic_project.search_image.data.remote.models.response.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getListRecommend(data: String) : Flow<ResultWrapper<BaseResponse>>
    fun getListImage(page: Int, search: String): Flow<ResultWrapper<BaseResponse>>
    fun getListHistory(): List<String>
    fun addHistory(data: String)
}