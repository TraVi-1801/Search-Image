package com.vic_project.search_image.utils.flow

import com.vic_project.search_image.data.remote.models.response.BaseResponse
import com.vic_project.search_image.domain.models.NetworkResponseState
import com.vic_project.search_image.data.remote.models.response.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart


object FlowUtils {
    fun Flow<ResultWrapper<BaseResponse>>.emitLoading(): Flow<ResultWrapper<BaseResponse>> {
        return onStart { emit(ResultWrapper.Loading) }
    }

    suspend fun Flow<ResultWrapper<BaseResponse>>.useDefaultCollector(
        _commonUiState: MutableStateFlow<NetworkResponseState>,
        doOnSuccess: (String) -> Unit
    ) {
        collect {
            when (it) {
                is ResultWrapper.Success -> {
                    doOnSuccess.invoke(it.value.data())
                    _commonUiState.value = NetworkResponseState.Success()
                }

                is ResultWrapper.Error -> {
                    _commonUiState.value = NetworkResponseState.Error(it.code, it.message)
                }

                is ResultWrapper.Loading -> {
                    _commonUiState.value = NetworkResponseState.Loading
                }
            }
        }
    }
}