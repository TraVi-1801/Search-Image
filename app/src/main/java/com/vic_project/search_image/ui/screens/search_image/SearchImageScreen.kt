package com.vic_project.search_image.ui.screens.search_image

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vic_project.search_image.R
import com.vic_project.search_image.domain.models.ListState
import com.vic_project.search_image.ui.custom.ImageLoad
import com.vic_project.search_image.ui.custom.InputText
import com.vic_project.search_image.ui.custom.boundsTransform
import com.vic_project.search_image.ui.custom.customSharedElement
import com.vic_project.search_image.ui.models.ImageModel
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.LogUtils.logger
import com.vic_project.search_image.utils.android.JSON.toJson
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickOutSideToHideKeyBoard
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchImageScreen(
    onNavToImageDetail: (ImageModel) -> Unit,
    viewModel: SearchImageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mainSearch by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.image_search))
    val error by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val finding by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.finding))
    val refreshAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.refresh))
    val pagingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pagin))
    val imagesState = rememberLazyStaggeredGridState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            uiState.canPaginate && (imagesState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (imagesState.layoutInfo.totalItemsCount - 10)
        }
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {
        viewModel.handleEvent(
            SearchImageEvent.PullRefresh
        )
    })
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && uiState.listState == ListState.IDLE) {
            viewModel.handleEvent(
                SearchImageEvent.GetListImage
            )
        }
    }
    LaunchedEffect(uiState.listImage) {
        if (uiState.listImage.isEmpty()){
            imagesState.scrollToItem(0)
        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickOutSideToHideKeyBoard()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ){
        val (text, search, listRecommence, listImage, refresh, paging, empty) = createRefs()

        Text(
            text = "The best free stock photos, royalty free images shared by creators.",
            style = AppTheme.typography.h4SemiBold,
            fontWeight = FontWeight.W500,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        InputText(
            modifier = Modifier.constrainAs(search) {
                top.linkTo(text.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            string = uiState.search,
            textHint = "Search for free photos",
            imgTrailing = R.drawable.ic_search,
            imeAction = ImeAction.Search,
            onSearch = {
                if (uiState.search.isNotEmpty()) {
                    viewModel.handleEvent(
                        SearchImageEvent.SearchImage(uiState.search)
                    )
                }
            },
            onFocusChanged = {
                if (it.hasFocus){
                    viewModel.handleEvent(
                        SearchImageEvent.UpdateFocus(true)
                    )
                } else {
                    viewModel.handleEvent(
                        SearchImageEvent.UpdateFocus(false)
                    )
                }
            }
        ) {
            viewModel.handleEvent(
                SearchImageEvent.UpdateSearch(it)
            )
        }

        AnimatedVisibility(
            visible = uiState.listImage.isEmpty(),
            modifier = Modifier
                .constrainAs(empty) {
                top.linkTo(search.bottom, margin = 16.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    (uiState.page == 1 && uiState.listState == ListState.LOADING) -> {
                        Spacer(Modifier.height(30.dp))
                        LottieAnimation(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .aspectRatio(1f),
                            composition = finding,
                            isPlaying = true,
                            restartOnPlay = false,
                            iterations = LottieConstants.IterateForever,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    (uiState.page == 1 && uiState.listState == ListState.ERROR) -> {
                        LottieAnimation(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .aspectRatio(1f),
                            composition = error,
                            isPlaying = true,
                            iterations = LottieConstants.IterateForever,
                            contentScale = ContentScale.Crop,
                        )
                        Text(
                            text = "An error occurred please try again.",
                            style = AppTheme.typography.h4Regular,
                            fontWeight = FontWeight.W400,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Try again",
                            style = AppTheme.typography.caption1Bold,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            color = AppTheme.colors.neutral10,
                            modifier = Modifier
                                .background(AppTheme.colors.primaryMain, RoundedCornerShape(16.dp))
                                .clickableSingle {
                                    viewModel.handleEvent(
                                        SearchImageEvent.GetListImage
                                    )
                                }
                                .padding(16.dp)
                        )

                    }

                    else -> {
                        if (uiState.isNotFound) {
                            Spacer(Modifier.height(32.dp))
                            Image(
                                painter = painterResource(R.drawable.img_not_found),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth(0.65f)
                                    .aspectRatio(1f)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "No image found",
                                style = AppTheme.typography.h4SemiBold,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Please search for photos with a different keyword or topic.",
                                style = AppTheme.typography.caption1Regular,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            LottieAnimation(
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .aspectRatio(1f),
                                composition = mainSearch,
                                isPlaying = true,
                                iterations = LottieConstants.IterateForever,
                                contentScale = ContentScale.Crop,
                            )
                            Text(
                                text = "Start searching for any photo you want to find.",
                                style = AppTheme.typography.h4Regular,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(refresh) {
                    top.linkTo(search.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    visibility =
                        if (uiState.pullRefresh && uiState.listState == ListState.LOADING) Visibility.Visible else Visibility.Invisible
                },
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = uiState.pullRefresh && uiState.listState == ListState.LOADING,
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .aspectRatio(1f),
                    composition = refreshAnimation,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.Crop,
                )
            }
        }

        AnimatedVisibility(
            visible = uiState.listImage.isNotEmpty(),
            modifier = Modifier
                .constrainAs(listImage) {
                    top.linkTo(refresh.bottom)
                    bottom.linkTo(paging.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .pullRefresh(pullRefreshState),
                state = imagesState,
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(uiState.listImage, key = ({ photo -> photo.id })) { photo ->
                    ImageLoad(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .clickableSingle{
                                onNavToImageDetail.invoke(
                                    photo
                                )
                            }
                            .wrapContentHeight(),
                        url = photo.urlSmall.orEmpty(),
                        contentDescription = photo.description,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(paging) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    visibility = if (uiState.listState == ListState.PAGINATING) Visibility.Visible else Visibility.Invisible
                },
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = uiState.listState == ListState.PAGINATING,
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(0.065f)
                        .aspectRatio(1f),
                    composition = pagingAnimation,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.Crop,
                )
            }
        }

        AnimatedVisibility(
            visible = uiState.listRecommend.isNotEmpty() || (uiState.onFocus && uiState.search.isBlank() && uiState.listHistory.isNotEmpty()),
            modifier = Modifier
                .constrainAs(listRecommence) {
                    top.linkTo(search.bottom, margin = 8.dp)
                    start.linkTo(search.start)
                    end.linkTo(search.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.neutral10, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.onFocus && uiState.search.isBlank() && uiState.listHistory.isNotEmpty()){
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Search history",
                            style = AppTheme.typography.bodySmRegular,
                            fontWeight = FontWeight.W500,
                        )
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "",
                            tint = AppTheme.colors.neutral100,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    uiState.listHistory.forEach {
                        Text(
                            text = it,
                            style = AppTheme.typography.bodyMdRegular,
                            color = AppTheme.colors.neutral80,
                            fontWeight = FontWeight.W500,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickableSingle {
                                    viewModel.handleEvent(
                                        SearchImageEvent.SearchImage(it)
                                    )
                                }
                        )
                    }
                } else {
                    Text(
                        text = "Search suggestions",
                        style = AppTheme.typography.bodySmRegular,
                        fontWeight = FontWeight.W500,
                    )
                    uiState.listRecommend.forEach {
                        Text(
                            text = it,
                            style = AppTheme.typography.bodyMdRegular,
                            color = AppTheme.colors.neutral80,
                            fontWeight = FontWeight.W500,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickableSingle {
                                    viewModel.handleEvent(
                                        SearchImageEvent.SearchImage(it)
                                    )
                                }
                        )
                    }
                }
            }
        }

    }
}