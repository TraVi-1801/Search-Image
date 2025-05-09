package com.vic_project.search_image.ui.base

import androidx.compose.runtime.Composable
import com.vic_project.search_image.ui.custom.DialogLoading
import com.vic_project.search_image.ui.custom.HRMDefaultDialog
import com.vic_project.search_image.utils.NetworkConstant
import com.vic_project.search_image.domain.models.NetworkResponseState

@Composable
fun HandleCommonUiState(
    networkResponseState: NetworkResponseState,
    onClick: () -> Unit = {}
) {
    when (networkResponseState) {
        is NetworkResponseState.Loading -> {
            DialogLoading()
        }

        is NetworkResponseState.Error -> {
            when {
                (networkResponseState.code == NetworkConstant.TimeOut) -> {
                    HRMDefaultDialog(
                        title = "Unstable Network Connection",
                        message = "Please check your internet connection and try again!",
                        onClick = onClick
                    )
                }
                else -> {
                    HRMDefaultDialog(
                        title = "Lỗi",
                        message = networkResponseState.message ?: "",
                        onClick = onClick
                    )
                }
            }
        }

        else -> {

        }
    }
}
