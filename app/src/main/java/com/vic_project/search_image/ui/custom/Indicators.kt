package com.vic_project.search_image.ui.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.ui.theme.AppTheme

@Composable
fun PageIndication(
    size: Int,
    current: Int,
    color1: Color = AppTheme.colors.neutral10,
    color2: Color = AppTheme.colors.neutral10.copy(0.5f),
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(size) { iteration ->
                val color: Color by animateColorAsState(
                    targetValue = if (current == iteration) {
                        color1
                    } else {
                        color2
                    },
                    animationSpec = tween(
                        durationMillis = 500,
                    ), label = ""
                )
                val size: Dp by animateDpAsState(
                    targetValue = if (current == iteration) {
                        22.dp
                    } else {
                        10.dp
                    },
                    animationSpec = tween(
                        durationMillis = 500,
                    ), label = ""
                )


                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(horizontal = 4.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                        .border(4.dp, color, shape = RoundedCornerShape(4.dp))
                        .width(size)
                        .height(6.dp)

                )
            }
        }
    }
}