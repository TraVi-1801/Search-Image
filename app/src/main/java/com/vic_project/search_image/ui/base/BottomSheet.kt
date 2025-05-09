package com.vic_project.search_image.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle


@Composable
fun BottomSheetBase(
    content: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit,
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .clickableSingle {
                    onDismiss()
                }
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                    .clickableSingle {}
                    .fillMaxWidth()
                    .background(AppTheme.colors.neutral10)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()
            }
        }
    }
}