package com.paf.synthlib.common.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paf.synthlib.ui.theme.Orange

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: Int,
    backgroundColor: Color = Orange,
    foregroundColor: Color = Color.White,
    isEnabled: Boolean = true,
    iconId: Int? = null,
    showIcon: Boolean = false,
    onClick: () -> Unit
) {
    AppButton(
        modifier,
        stringResource(text),
        backgroundColor,
        foregroundColor,
        isEnabled,
        iconId,
        showIcon,
        onClick
    )
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Orange,
    foregroundColor: Color = Color.White,
    isEnabled: Boolean = true,
    iconId: Int? = null,
    showIcon: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = foregroundColor,
            disabledBackgroundColor = backgroundColor.copy(alpha = ContentAlpha.disabled)
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        enabled = isEnabled
    ) {
        val textColor = foregroundColor.apply {
            if (!isEnabled)
                this.copy(alpha = ContentAlpha.disabled)
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            if (showIcon && iconId != null) {
                Icon(
                    painter = painterResource(iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                    tint = textColor
                )
            }
        }
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    AppButton(text = "Test App") {}
}

@Preview
@Composable
fun AppButtonWithIconPreview() {
    AppButton(text = "Test App", showIcon = true) {}
}