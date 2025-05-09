package com.vic_project.search_image.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@Composable
fun FullDialogSuccess(
    title: String,
    description: String,
    btnText: String,
    onDialogDismiss: () -> Unit,
    onClick: () -> Unit,
) {
    FullScreenDialog (
        onDialogDismiss = onDialogDismiss
    ){
        Box(modifier = Modifier.fillMaxSize()){

            Column (
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                /*Image(
                    painter = painterResource(R.drawable.img_success),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .aspectRatio(1f)
                )*/
                Text(
                    text = title,
                    fontWeight = FontWeight.W600,
                    style = AppTheme.typography.bodyMdSemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                Text(
                    text = description,
                    fontWeight = FontWeight.W400,
                    style = AppTheme.typography.bodySmRegular,
                    color = AppTheme.colors.neutral80,
                    textAlign = TextAlign.Center,
                )
            }

            Text(
                text = btnText,
                fontWeight = FontWeight.W500,
                style = AppTheme.typography.bodyMdMedium,
                color = AppTheme.colors.neutral10,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 30.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .clickableSingle {
                        onClick.invoke()
                    }
                    .background(AppTheme.colors.primaryMain, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            )
        }
    }
}