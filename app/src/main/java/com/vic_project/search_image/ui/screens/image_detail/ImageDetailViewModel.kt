package com.vic_project.search_image.ui.screens.image_detail

import androidx.lifecycle.SavedStateHandle
import com.vic_project.search_image.ui.base.BaseViewModel
import com.vic_project.search_image.ui.models.ImageModel
import com.vic_project.search_image.utils.LogUtils.logger
import com.vic_project.search_image.utils.android.JSON
import com.vic_project.search_image.utils.android.JSON.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ImageDetailState, ImageDetailEvent>(ImageDetailState()) {
    private val data = savedStateHandle.get<ImageModel>("image")

    init {
        handleEvent(
            ImageDetailEvent.UpdateImage(
                data
            )
        )
    }

    override fun handleEvent(event: ImageDetailEvent) {
        when (event) {
            is ImageDetailEvent.UpdateImage -> {
                updateUiState(
                    uiState.value.copy(
                        data = event.data
                    )
                )
            }
        }
    }
}

sealed interface ImageDetailEvent {
    data class UpdateImage(val data: ImageModel?) : ImageDetailEvent
}

data class ImageDetailState(
    val data: ImageModel? = null
)