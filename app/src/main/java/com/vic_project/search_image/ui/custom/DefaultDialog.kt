package com.vic_project.search_image.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@Composable
fun HRMDefaultDialog(
    title: String,
    message: String,
    dismissOutside: Boolean = true,
    onClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(true) }
    var shouldDismissOutside by remember(key1 = dismissOutside) {
        mutableStateOf(dismissOutside)
    }
    val onDismissRequest = {
        if (shouldDismissOutside) {
            showDialog = showDialog.not()
            onClick.invoke()
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.85f)
                        .align(Alignment.Center)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(color = AppTheme.colors.neutral100),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(13.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    AppTheme.colors.primaryMain,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickableSingle {
                                    if (!shouldDismissOutside) {
                                        shouldDismissOutside = true
                                    }
                                    onDismissRequest()
                                    onClick()
                                }
                                .padding(10.dp),
                            text = "OK",
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral10),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HRMDefaultDialogImgError(
    img: @Composable () -> Unit,
    title: String,
    message: String?,
    messageOther: AnnotatedString = buildAnnotatedString {  },
    btnText: String = "OK",
    dismissOutside: Boolean = true,
    isSetPadding: Boolean = false,
    onClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(true) }
    var shouldDismissOutside by remember(key1 = dismissOutside) {
        mutableStateOf(dismissOutside)
    }
    val onDismissRequest = {
        if (shouldDismissOutside) {
            showDialog = showDialog.not()
            onClick.invoke()
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .then(
                            if (isSetPadding){
                                Modifier
                                    .fillMaxWidth(0.85f)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                            }
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.Center)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        img.invoke()
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(color = AppTheme.colors.neutral100),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        message?.let {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = message,
                                style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                                textAlign = TextAlign.Center
                            )
                        } ?: run {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = messageOther,
                                style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    AppTheme.colors.primaryMain,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickableSingle {
                                    if (!shouldDismissOutside) {
                                        shouldDismissOutside = true
                                    }
                                    onDismissRequest()
                                    onClick()
                                }
                                .padding(10.dp),
                            text = btnText,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral10),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun HRMDefaultDialogImg(
    img: @Composable () -> Unit,
    title: String,
    message: String?,
    messageOther: AnnotatedString = buildAnnotatedString {  },
    btnText: String = "OK",
    dismissOutside: Boolean = true,
    isSetPadding: Boolean = false,
    onClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(true) }
    var shouldDismissOutside by remember(key1 = dismissOutside) {
        mutableStateOf(dismissOutside)
    }
    val onDismissRequest = {
        if (shouldDismissOutside) {
            showDialog = showDialog.not()
            onClick.invoke()
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .then(
                            if (isSetPadding){
                                Modifier
                                    .fillMaxWidth(0.85f)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                            }
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.Center)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        img.invoke()
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(color = AppTheme.colors.neutral100),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        message?.let {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = message,
                                style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                                textAlign = TextAlign.Center
                            )
                        } ?: run {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = messageOther,
                                style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    AppTheme.colors.primaryMain,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickableSingle {
                                    onClick.invoke()
                                }
                                .padding(10.dp),
                            text = btnText,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral10),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HRMDefaultDialogImgTowButton(
    img: @Composable () -> Unit,
    title: String,
    message: String,
    btnTextOne: String = "Huỷ",
    btnText: String = "OK",
    dismissOutside: Boolean = true,
    isSetPadding: Boolean = false,
    onCancel: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    var showDialog by remember { mutableStateOf(true) }
    var shouldDismissOutside by remember(key1 = dismissOutside) {
        mutableStateOf(dismissOutside)
    }
    val onDismissRequest = {
        if (shouldDismissOutside) {
            showDialog = showDialog.not()
            onCancel.invoke()
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickableSingle { onDismissRequest.invoke() }
            ) {
                Box(
                    Modifier
                        .then(
                            if (isSetPadding){
                                Modifier
                                    .fillMaxWidth(0.85f)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                            }
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.Center)
                        .clickableSingle {  }
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        img.invoke()
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(color = AppTheme.colors.neutral100),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        1.dp,
                                        AppTheme.colors.neutral40,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickableSingle {
                                        onCancel.invoke()
                                    }
                                    .padding(10.dp),
                                text = btnTextOne,
                                style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral100),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        AppTheme.colors.primaryMain,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .clickableSingle {
                                        onCancel.invoke()
                                        onClick()
                                    }
                                    .padding(10.dp),
                                text = btnText,
                                style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral10),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HRMDefaultDialogImgTowButtonVertically(
    img: @Composable () -> Unit,
    title: String,
    message: String,
    btnTextOne: String = "Huỷ",
    btnText: String = "OK",
    dismissOutside: Boolean = true,
    isSetPadding: Boolean = false,
    onCancel: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    var showDialog by remember { mutableStateOf(true) }
    var shouldDismissOutside by remember(key1 = dismissOutside) {
        mutableStateOf(dismissOutside)
    }
    val onDismissRequest = {
        if (shouldDismissOutside) {
            showDialog = showDialog.not()
            onCancel.invoke()
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickableSingle { onDismissRequest.invoke() }
            ) {
                Box(
                    Modifier
                        .then(
                            if (isSetPadding){
                                Modifier
                                    .fillMaxWidth(0.85f)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                            }
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.Center)
                        .clickableSingle {  }
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        img.invoke()
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(color = AppTheme.colors.neutral100),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral70),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    AppTheme.colors.primaryMain,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickableSingle {
                                    onCancel.invoke()
                                    onClick()
                                }
                                .padding(10.dp),
                            text = btnText,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral10),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    AppTheme.colors.neutral40,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickableSingle {
                                    onCancel.invoke()
                                }
                                .padding(10.dp),
                            text = btnTextOne,
                            style = MaterialTheme.typography.bodyMedium.copy(color = AppTheme.colors.neutral100),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HRMDefaultDialogError(
    title: String,
    message: String,
    dismissOutside: Boolean = true,
    onClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(true) }
    var shouldDismissOutside by remember(key1 = dismissOutside) {
        mutableStateOf(dismissOutside)
    }
    val onDismissRequest = {
        if (shouldDismissOutside) {
            showDialog = showDialog.not()
            onClick.invoke()
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = title,
                                style = AppTheme.typography.bodySmSemiBold.copy(color = AppTheme.colors.neutral100),
                                modifier = Modifier.weight(1f),
                            )
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "",
                                tint = AppTheme.colors.neutral70,
                                modifier = Modifier.size(16.dp).clickableSingle { onClick.invoke() }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message,
                            style = AppTheme.typography.caption1Regular.copy(color = AppTheme.colors.neutral70),
                        )
                    }
                }
            }
        }
    }
}