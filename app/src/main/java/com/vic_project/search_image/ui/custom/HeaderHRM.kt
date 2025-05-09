package com.vic_project.search_image.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.R
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@Composable
fun HeaderHRM(
    title: String,
    contentRight: @Composable (() -> Unit)? = null,
    contentBottom: @Composable (() -> Unit)? = null,
    onBack:() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                AppTheme.colors.neutral10,
                RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
            )
            .padding(16.dp),
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = "",
                tint = AppTheme.colors.neutral100,
                modifier = Modifier
                    .size(30.dp)
                    .clickableSingle {
                        onBack.invoke()
                    }
                    .padding(4.dp)
            )
            Text(
                text = title,
                style = AppTheme.typography.bodyLgSemiBold,
                fontWeight = FontWeight.W600
            )
            contentRight?.invoke()
        }
        contentBottom?.let {
            Spacer(Modifier.height(16.dp))
            it.invoke()
        }
    }
}


@Composable
@Preview
fun PreviewHeader(modifier: Modifier = Modifier) {
    HeaderHRM(
        title = "dàkldsfsldf ksdf ódfisodf ịdfoisdfj opisjdfsdf pọidf sdf sdf"
    ){

    }
}