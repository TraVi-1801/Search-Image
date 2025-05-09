package com.vic_project.search_image.ui.custom

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vic_project.search_image.ui.theme.AppTheme

@Composable
fun DialogLoading() {
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

    Dialog(
        onDismissRequest = {}
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
                    .background(AppTheme.colors.neutral10, RoundedCornerShape(16.dp))
                    .align(Alignment.Center),
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(size = 32.dp)
                        .rotate(degrees = rotateAnimation)
                        .border(
                            width = 2.dp,
                            brush = Brush.sweepGradient(
                                listOf(
                                    AppTheme.colors.primaryMain,
                                    AppTheme.colors.neutral50
                                )
                            ),
                            shape = CircleShape
                        )
                        .align(Alignment.Center),
                    progress = 1f,
                    strokeWidth = 1.dp,
                    color = AppTheme.colors.neutral50
                )
            }
        }
    }
}