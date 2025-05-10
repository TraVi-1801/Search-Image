package com.vic_project.search_image.ui.custom

import android.graphics.Bitmap
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.scale
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.vic_project.search_image.R
import com.vic_project.search_image.ui.theme.AppTheme

class ScaledBitmapTransformation(
    private val scale: Float,
    private val url: String
) : Transformation {
    override val cacheKey: String
        get() = "${url}_${scale}"
    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        return input.scale((input.width * scale).toInt(), (input.height * scale).toInt())
    }
}

@Composable
fun ImageLoad(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
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

    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        loading = {
            Box(
                modifier = modifier
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                ImageShimmer(modifier = Modifier.matchParentSize())
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(rotateAnimation)
                        .border(
                            width = 2.dp,
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
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.image_placeholder),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier
                    .aspectRatio(1f)
            )
        },
        success = { imageState ->
            SubcomposeAsyncImageContent()
        }
    )
}


@Composable
fun ImageShimmer(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Spacer(
        modifier = modifier
            .aspectRatio(1f)
            .border(1.5.dp, brush, RoundedCornerShape(8.dp))
    )
}
