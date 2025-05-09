package com.vic_project.search_image.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.vic_project.search_image.ui.theme.AppTheme
import kotlinx.coroutines.delay

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SplashScreen(
    onNavSearch:() -> Unit,
) {
    val textDescription = "Ready to search for free photos"
    var textToShow by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        textDescription.forEachIndexed { index, _ ->
            textToShow = textDescription.substring(0, index + 1)
            delay(30)
        }
        onNavSearch.invoke()
    }

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.neutral20)
            .padding(horizontal = 16.dp)
    ){
        val (text, boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix, boxSeven) = createRefs()
        Text(
            text = textToShow,
            fontWeight = FontWeight.W700,
            color = AppTheme.colors.neutral100,
            style = AppTheme.typography.h2SemiBold,
            modifier = Modifier
                .constrainAs(text){
                    top.linkTo(parent.top, margin = 70.dp)
                    start.linkTo(parent.start)
                }
                .statusBarsPadding()
        )

        Box(
            modifier = Modifier
                .constrainAs(boxOne){
                    top.linkTo(text.bottom, margin = 46.dp)
                    start.linkTo(parent.start)
                }
                .clip(CircleShape)
                .size(120.dp)
                .background(AppTheme.colors.neutral90, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Lifestyle",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral10,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(boxTwo){
                    top.linkTo(boxOne.top, margin = 26.dp)
                    start.linkTo(boxOne.end)
                    end.linkTo(parent.end)
                }
                .clip(CircleShape)
                .size(105.dp)
                .background(AppTheme.colors.neutral40, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Science",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral100,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(boxThree){
                    top.linkTo(boxOne.bottom, margin = 35.dp)
                    start.linkTo(parent.start)
                }
                .clip(CircleShape)
                .size(80.dp)
                .background(AppTheme.colors.neutral90, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Art",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral10,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(boxFour){
                    top.linkTo(boxOne.bottom, margin = 26.dp)
                    start.linkTo(boxThree.end)
                    end.linkTo(boxFive.start)
                }
                .clip(CircleShape)
                .size(95.dp)
                .background(AppTheme.colors.neutral40, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Sport",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral100,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(boxFive){
                    top.linkTo(boxOne.bottom, margin = 26.dp)
                    start.linkTo(boxFour.end)
                    end.linkTo(parent.end)
                }
                .clip(CircleShape)
                .size(95.dp)
                .background(AppTheme.colors.neutral40, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Politics",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral100,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(boxSeven){
                    top.linkTo(boxThree.bottom, margin = 30.dp)
                    end.linkTo(parent.end)
                }
                .clip(CircleShape)
                .size(130.dp)
                .background(AppTheme.colors.neutral90, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Design Tools",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral10,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(boxSix){
                    top.linkTo(boxSeven.top)
                    end.linkTo(boxSeven.start, margin = 25.dp)
                    bottom.linkTo(boxSeven.bottom)
                }
                .clip(CircleShape)
                .size(120.dp)
                .background(AppTheme.colors.neutral40, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Nature",
                fontWeight = FontWeight.W500,
                color = AppTheme.colors.neutral100,
                style = AppTheme.typography.bodySmMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}