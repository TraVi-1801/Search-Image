package com.vic_project.search_image.ui.screens.search_image

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.vic_project.search_image.data.remote.network.NetworkMonitor
import com.vic_project.search_image.domain.models.ListState
import com.vic_project.search_image.domain.models.Photo
import com.vic_project.search_image.domain.models.RecommendSearchData
import com.vic_project.search_image.domain.repositories.SearchRepository
import com.vic_project.search_image.ui.base.BaseViewModel
import com.vic_project.search_image.ui.models.ImageModel
import com.vic_project.search_image.utils.android.JSON
import com.vic_project.search_image.utils.android.JSON.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchImageViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val networkMonitor: NetworkMonitor
) : BaseViewModel<SearchImageState, SearchImageEvent>(SearchImageState()) {

    init {
        uiState.distinctUntilChangedBy { it.onFocus}
            .onEach {
                if (it.onFocus){
                    val list = searchRepository.getListHistory()
                    updateUiState(
                        uiState.value.copy(
                            listHistory = list
                        )
                    )
                    if (it.search.isNotBlank()){
                        getListRecommend(it.search)
                    }
                } else {
                    updateUiState(
                        uiState.value.copy(
                            listRecommend = emptyList()
                        )
                    )
                }
        }.launchIn(viewModelScope)

        networkMonitor.isOnline.distinctUntilChangedBy { it }.onEach {
            if (it && uiState.value.retry){
                updateUiState(
                    uiState.value.copy(
                        retry = false
                    )
                )
                getImagesTryAgain()
            }
        }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: SearchImageEvent) {
        when (event) {
            is SearchImageEvent.UpdateSearch -> {
                updateUiState(
                    uiState.value.copy(
                        search = event.data
                    )
                )
                getListRecommend(event.data)
            }

            SearchImageEvent.GetListImage -> {
                getImages()
            }

            is SearchImageEvent.SearchImage -> {
                updateUiState(
                    uiState.value.copy(
                        currentSearch = event.data,
                        search = event.data,
                        page = 1,
                        canPaginate = false,
                        listState = ListState.IDLE,
                        listRecommend = emptyList(),
                        listImage = emptyList(),
                    )
                )
                getImages()
            }

            SearchImageEvent.PullRefresh -> {
                updateUiState(
                    uiState.value.copy(
                        page = 1,
                        canPaginate = false,
                        pullRefresh = true,
                        listState = ListState.IDLE
                    )
                )
                getImages()
            }

            is SearchImageEvent.UpdateFocus -> {
                updateUiState(
                    uiState.value.copy(
                        onFocus = event.data
                    )
                )
            }
        }
    }

    private fun getImagesTryAgain() {
        async {
            searchRepository.getListImage(
                search = uiState.value.currentSearch,
                page = uiState.value.page
            ).collectNetworkState(
                showLoading = false,
                showError = false,
                doOnError = {
                    updateUiState(
                        uiState.value.copy(
                            pullRefresh = false,
                            listState = ListState.ERROR,
                            listImage = if (uiState.value.page == 1) emptyList() else uiState.value.listImage
                        )
                    )
                },
                doOnTryAgain = {
                    updateUiState(
                        uiState.value.copy(
                            retry = true
                        )
                    )
                }
            ) {
                handleImagesResponse(it)
            }
        }
    }

    private fun getImages() {
        async {
            if (uiState.value.page == 1 || (uiState.value.page != 1 && uiState.value.canPaginate) && uiState.value.listState == ListState.IDLE) {
                updateUiState(
                    uiState.value.copy(
                        listState = if (uiState.value.page == 1) ListState.LOADING else ListState.PAGINATING,
                    )
                )
                searchRepository.getListImage(
                    search = uiState.value.currentSearch,
                    page = uiState.value.page
                ).collectNetworkState(
                    showLoading = false,
                    showError = false,
                    doOnError = {
                        updateUiState(
                            uiState.value.copy(
                                pullRefresh = false,
                                listState = ListState.ERROR,
                                listImage = if (uiState.value.page == 1) emptyList() else uiState.value.listImage
                            )
                        )
                    },
                    doOnTryAgain = {
                        updateUiState(
                            uiState.value.copy(
                                retry = true
                            )
                        )
                    }
                ) {
                    handleImagesResponse(it)
                }
            }
        }
    }

    private fun getListRecommend(data: String) {
        if (data.isBlank()) {
            updateUiState(
                uiState.value.copy(
                    listRecommend = emptyList()
                )
            )
        } else {
            async {
                searchRepository.getListRecommend(data).collectNetworkState(
                    showLoading = false,
                    showError = false
                ) {
                    val list = JSON.decode(
                        it,
                        RecommendSearchData::class.java
                    )?.attributes?.suggestions.orEmpty().take(5)
                    updateUiState(
                        uiState.value.copy(
                            listRecommend = list
                        )
                    )
                }
            }
        }
    }

    private fun handleImagesResponse(response: String) {
        val imagesResponse = JSON.decodeToList(
            response,
            Array<Photo>::class.java
        ).orEmpty().map { it.convertImageData() }
        val isNotFound = uiState.value.page == 1 && imagesResponse.isEmpty()

        if (imagesResponse.isNotEmpty()) {
            if (uiState.value.page == 1){
                searchRepository.addHistory(uiState.value.currentSearch)
            }

            val canPaginate = imagesResponse.size == 40
            val listData = uiState.value.listImage + imagesResponse
            updateUiState(
                uiState.value.copy(
                    listImage = listData,
                    canPaginate = canPaginate,
                    pullRefresh = false,
                    isNotFound = isNotFound,
                    listState = if (canPaginate) ListState.IDLE else ListState.PAGINATION_EXHAUST,
                    page = if (canPaginate) uiState.value.page + 1 else uiState.value.page
                )
            )
        } else {
            updateUiState(
                uiState.value.copy(
                    pullRefresh = false,
                    isNotFound = isNotFound,
                    canPaginate = false,
                    listState = ListState.PAGINATION_EXHAUST,
                )
            )
        }
    }

    override fun onCleared() {
        updateUiState(
            uiState.value.copy(
                page = 1,
                canPaginate = false,
                listState = ListState.IDLE,
            )
        )
        super.onCleared()
    }
}

sealed interface SearchImageEvent {
    data class UpdateSearch(val data: String) : SearchImageEvent
    data class SearchImage(val data: String) : SearchImageEvent
    data class UpdateFocus(val data: Boolean) : SearchImageEvent
    data object PullRefresh : SearchImageEvent
    data object GetListImage : SearchImageEvent
}

data class SearchImageState(
    val search: String = "",
    val currentSearch: String = "",
    val listRecommend: List<String> = emptyList(),
    val listHistory: List<String> = emptyList(),
    val listImage: List<ImageModel> = emptyList(),
    val isNotFound: Boolean = false,
    val retry: Boolean = false,
    val pullRefresh: Boolean = false,
    val onFocus: Boolean = false,
    val page: Int = 1,
    val canPaginate: Boolean = false,
    val listState: ListState = ListState.IDLE
)