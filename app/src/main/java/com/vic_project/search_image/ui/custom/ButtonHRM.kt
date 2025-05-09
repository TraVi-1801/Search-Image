package com.vic_project.search_image.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@Composable
fun ButtonHRM(
    title: String,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Text(
        text = title,
        fontWeight = FontWeight.W500,
        style = AppTheme.typography.bodyMdMedium,
        color = AppTheme.colors.neutral10,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(enable) {
                onClick.invoke()
            }
            .background(
                AppTheme.colors.primaryMain,
                RoundedCornerShape(50.dp)
            )
            .padding(16.dp)
    )
}

@Composable
fun ButtonOutLineHRM(
    title: String,
    enable: Boolean = true,
    colorLine: Color = AppTheme.colors.neutral40,
    colorText: Color = AppTheme.colors.neutral100,
    onClick: () -> Unit
) {
    Text(
        text = title,
        fontWeight = FontWeight.W500,
        style = AppTheme.typography.bodyMdMedium,
        textAlign = TextAlign.Center,
        color = colorText,
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle(enable) {
                onClick.invoke()
            }
            .background(
                AppTheme.colors.neutral10,
                RoundedCornerShape(50.dp)
            )
            .border(1.dp,colorLine, RoundedCornerShape(50.dp))
            .padding(16.dp)
    )
}

@Composable
@Preview
fun PreviewButtonHRM() {
    ButtonHRM(
        title = "test"
    ) { }
}

@Composable
fun ButtonHRMBottomSheet(
    title: String,
    isNavigatePadding: Boolean = false,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.neutral10, RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
            .then(
                if (isNavigatePadding)
                Modifier.navigationBarsPadding() else Modifier
            )
            .padding(16.dp)
    ){
        Text(
            text = title,
            fontWeight = FontWeight.W500,
            style = AppTheme.typography.bodyMdMedium,
            color = if (enable)  AppTheme.colors.neutral10 else AppTheme.colors.neutral50,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickableSingle(enable) {
                    onClick.invoke()
                }
                .background(
                    if (enable) AppTheme.colors.primaryMain else AppTheme.colors.neutral20,
                    RoundedCornerShape(50.dp)
                )
                .padding(16.dp)
        )
    }
}

@Composable
@Preview
fun PreviewButtonHRMBottomSheet() {
    ButtonHRMBottomSheet(
        title = "test"
    ) { }
}