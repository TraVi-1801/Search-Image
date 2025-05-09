package com.vic_project.search_image.ui.custom

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.ui.theme.AppTheme

@Composable
@Preview
fun LoadingCircular(
    size: Dp = 32.dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 360,
                easing = LinearEasing
            )
        ), label = ""
    )

    CircularProgressIndicator(
        modifier = Modifier
            .size(size = size)
            .rotate(degrees = rotateAnimation)
            .border(
                width = 4.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        AppTheme.colors.primaryMain,
                        AppTheme.colors.neutral50
                    )
                ),
                shape = CircleShape
            ),
        progress = 1f,
        strokeWidth = 1.dp,
        color = AppTheme.colors.neutral50
    )
}