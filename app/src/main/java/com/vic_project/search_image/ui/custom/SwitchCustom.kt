package com.vic_project.search_image.ui.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@Composable
fun SwitchCustom(
    checked :Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    val alphaAnim = animateIntAsState(
        targetValue = if (checked) 1 else 0,
        animationSpec = tween(
            durationMillis = 150
        )
    )

    val colorAnim = animateColorAsState(
        targetValue = if (checked) AppTheme.colors.primaryMain else AppTheme.colors.neutral40,
        animationSpec = tween(
            durationMillis = 200
        )
    )


    Row (
        modifier = Modifier
            .width(42.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(colorAnim.value, RoundedCornerShape(50.dp))
            .clickableSingle {onCheckedChange.invoke(!checked)}
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        if (alphaAnim.value == 1){
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(AppTheme.colors.neutral10)
        )
        if (alphaAnim.value != 1){
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}