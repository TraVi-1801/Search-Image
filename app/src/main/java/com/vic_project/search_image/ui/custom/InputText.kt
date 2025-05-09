package com.vic_project.search_image.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vic_project.search_image.R
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle
import com.vic_project.search_image.utils.compose.rememberImeState

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    leadingTitle: String = "",
    string: String,
    textHint: String,
    imgTrailing: Int? = null,
    imgTrailingTint: Color? = null,
    leadingIcon: Int? = null,
    leadingIconTint: Color? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    errorMessage: String = "",
    lines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    isPassword: Boolean = false,
    error: Boolean = false,
    isShowIconPassword: Boolean = isPassword,
    keyType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onClick: () -> Unit = {},
    onSearch: () -> Unit = {},
    disabledContainerColor: Color = AppTheme.colors.neutral10,
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChange: (String) -> Unit
) {
    val imeState = rememberImeState()
    val check = remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }

    val focusInputOne = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            check.value = true
        }
        if (!imeState.value && check.value) {
            focusManager.clearFocus()
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = string,
            enabled = enabled,
            onValueChange = {
                onValueChange.invoke(it)
            },
            minLines = lines,
            maxLines = maxLines,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    focusInputOne.value = focusState.hasFocus
                    onFocusChanged.invoke(focusState)
                }
                .border(
                    if (focusInputOne.value) 2.dp else 1.dp,
                    when {
                        errorMessage.isNotEmpty() || error -> AppTheme.colors.dangerMain
                        focusInputOne.value -> AppTheme.colors.primaryMain
                        else -> AppTheme.colors.neutral40
                    },
                    RoundedCornerShape(8.dp)
                )
                .background(disabledContainerColor, RoundedCornerShape(8.dp))
                .clickableSingle { onClick.invoke() },
            textStyle = AppTheme.typography.bodySmRegular.copy(
                fontWeight = FontWeight.W500,
                color = if (enabled) AppTheme.colors.neutral100 else AppTheme.colors.neutral80,
            ),
            visualTransformation = if (isPassword) {
                if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation()
            } else VisualTransformation.None,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
                onSearch = {
                    focusManager.clearFocus()
                    onSearch.invoke()
                }
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            painter = painterResource(id = leadingIcon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(18.dp),
                            tint = leadingIconTint ?: LocalContentColor.current
                        )
                    }
                    if (leadingTitle.isNotEmpty()){
                        Text(
                            text = leadingTitle,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.W400,
                            color = AppTheme.colors.neutral100
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (string.isEmpty()) {
                            Text(
                                text = textHint,
                                style = AppTheme.typography.bodySmRegular,
                                fontWeight = FontWeight.W400,
                                color = AppTheme.colors.neutral80
                            )
                        }
                        innerTextField()
                    }
                    if (imgTrailing != null) {
                        Icon(
                            painter = painterResource(id = imgTrailing),
                            contentDescription = "",
                            modifier = Modifier
                                .size(18.dp)
                                .clickableSingle{
                                    onSearch.invoke()
                                },
                            tint = imgTrailingTint ?: LocalContentColor.current
                        )
                    }
                    if (isShowIconPassword) {
                        val icon = if (showPassword.value) {
                            R.drawable.ic_eye
                        } else {
                            R.drawable.ic_eyeclosed
                        }

                        IconButton(
                            modifier = Modifier.size(16.dp),
                            onClick = { showPassword.value = !showPassword.value }) {
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = "Visibility",
                                tint = Color.Black
                            )
                        }
                    }
                }
            },
        )
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = AppTheme.colors.dangerMain,
                style = AppTheme.typography.caption2Regular,
                modifier = Modifier.semantics { contentDescription = "ConfirmPasswordMessage" },
            )
        }
    }
}