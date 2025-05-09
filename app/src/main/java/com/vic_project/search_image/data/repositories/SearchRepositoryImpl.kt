package com.vic_project.search_image.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.vic_project.search_image.data.local.preferences.Preferences
import com.vic_project.search_image.data.remote.models.request.KeyRequest
import com.vic_project.search_image.data.remote.models.response.BaseResponse
import com.vic_project.search_image.data.remote.models.response.ResultWrapper
import com.vic_project.search_image.data.remote.service.RetrofitService
import com.vic_project.search_image.domain.models.AuthModel
import com.vic_project.search_image.domain.repositories.SearchRepository
import com.vic_project.search_image.utils.LogUtils.logger
import com.vic_project.search_image.utils.android.JSON
import com.vic_project.search_image.utils.android.JSON.toJson
import com.vic_project.search_image.utils.flow.FlowUtils.emitLoading
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named
import kotlin.text.get

class SearchRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val preferences: Preferences,
    @Named("io") private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {
    override suspend fun getListRecommend(data: String): Flow<ResultWrapper<BaseResponse>> {
        return flow {
            val response = retrofitService.getMethod(
                headers = AuthModel.headerWithContentType(),
                request = KeyRequest.RECOMMEND_KEY,
                message = "${data}?opensearch=true",
                codeRequired = KeyRequest.RECOMMEND_KEY.codeResponse
            )
            emit(response)
        }.emitLoading().flowOn(ioDispatcher)
    }

    override fun getListImage(page: Int, search: String): Flow<ResultWrapper<BaseResponse>> {
        return flow {
            val response = retrofitService.getMethod(
                headers = AuthModel.headerWithAPIKey(),
                request = KeyRequest.IMAGE_KEY,
                message = "page=${page}&per_page=40&query=${search}",
                codeRequired = KeyRequest.IMAGE_KEY.codeResponse
            )
            emit(response)
        }.emitLoading().flowOn(ioDispatcher)
    }

    override fun getListHistory(): List<String> {
        return JSON.decodeToList(preferences.listSearch, Array<String>::class.java).orEmpty().take(5)
    }

    override fun addHistory(data: String) {
        val listData = JSON.decodeToList(preferences.listSearch, Array<String>::class.java).orEmpty().toMutableList().also {
            it.remove(data)
            it.add(0,data)
        }

        preferences.listSearch = listData.toJson()
    }
}