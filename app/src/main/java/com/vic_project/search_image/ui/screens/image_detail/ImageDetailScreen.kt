package com.vic_project.search_image.ui.screens.image_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.TableInfo
import com.vic_project.search_image.R
import com.vic_project.search_image.ui.custom.ImageLoad
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ImageDetailScreen(
    onBack: () -> Unit,
    viewModel: ImageDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var scale by remember {
        mutableStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.neutral100)
    ) {
        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }

        ImageLoad(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { tapOffset ->
                            if (scale == 1f) {
                                scale = 3f
                                offset = calculateOffset(tapOffset, size)
                            } else {
                                scale = 1f
                                offset = Offset.Zero
                            }
                        }
                    )
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state),
            url = uiState.data?.url.orEmpty(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = "",
            tint = AppTheme.colors.neutral100,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 16.dp, start = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .size(32.dp)
                .background(AppTheme.colors.neutral40.copy(0.3f), RoundedCornerShape(12.dp))
                .clickableSingle {
                    onBack.invoke()
                }
                .padding(4.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colors.neutral40.copy(0.4f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = "Photo By: ${uiState.data?.photographer}",
                style = AppTheme.typography.bodyLgRegular,
                fontWeight = FontWeight.W500,
            )
            if (uiState.data?.description.isNullOrBlank().not()) {
                Text(
                    text = "Description: ${uiState.data?.description}",
                    style = AppTheme.typography.bodyMdMedium,
                    fontWeight = FontWeight.W500,
                )
            }
        }
    }
}

private fun calculateOffset(tapOffset: Offset, size: IntSize): Offset {
    val offsetX = (-(tapOffset.x - (size.width / 2f)) * 2f)
        .coerceIn(-size.width / 2f, size.width / 2f)
    return Offset(offsetX, 0f)
}