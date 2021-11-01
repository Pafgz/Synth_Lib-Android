package com.paf.synthlib.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paf.synthlib.ui.theme.DarkBrown
import com.paf.synthlib.ui.theme.LightGrey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    isReadOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions? = null,
    textStyle: TextStyle = TextStyle(
        color = Color.White,
        fontSize = 26.sp,
        textDecoration = TextDecoration.None
    ),
    showText: Boolean = true,
    showUnderline: Boolean = true,
    onTextChanged: (String) -> Unit = {},
    endButton: @Composable (Modifier) -> Unit = {}
) {
    val inputValue = remember {
        mutableStateOf(value)
    }

    val actions = keyboardActions ?: run {
        val focusManager = LocalFocusManager.current
        val keyboardManager = LocalSoftwareKeyboardController.current

        keyboardManager?.let {
            focusManager.getAppKeyboardActions(it)
        } ?: KeyboardActions.Default
    }

    Column(modifier = modifier) {

        if (hint.isNotBlank())
            Text(text = hint, modifier = Modifier.padding(bottom = 2.dp))

        Row {
            BasicTextField(
                value = value,
                onValueChange = { textInput ->
                    inputValue.value = textInput
                    onTextChanged.invoke(textInput)
                },
                textStyle = textStyle,
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                singleLine = true,
                keyboardActions = actions,
                keyboardOptions = keyboardOptions,
                visualTransformation = if (showText) VisualTransformation.None else PasswordVisualTransformation(),
                readOnly = isReadOnly,
                enabled = !isReadOnly
            )

            endButton.invoke(
                Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )
        }

        if (showUnderline)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LightGrey)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    AppTextField(value = "Pif Paf", hint = "First Name") {}
}

@ExperimentalComposeUiApi
internal fun FocusManager.getAppKeyboardActions(keyboardManager: SoftwareKeyboardController) =
    KeyboardActions(
        onDone = { keyboardManager.hide() },
        onGo = { keyboardManager.hide() },
        onNext = { moveFocus(FocusDirection.Down) },
        onPrevious = { moveFocus(FocusDirection.Previous) },
        onSearch = null,
        onSend = null
    )