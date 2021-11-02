package com.paf.synthlib.preset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paf.synthlib.R
import com.paf.synthlib.common.views.ClickableIcon
import com.paf.synthlib.ui.theme.SynthLibTheme

@Composable
fun DemoItemView(
    name: String,
    showDivider: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column {
        Surface(modifier = modifier
            .height(70.dp)
            .fillMaxSize()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = name, fontSize = 20.sp, modifier = Modifier.weight(1f))

                Row(modifier = Modifier
                    .weight(1f, false)
                    .wrapContentWidth()) {
                    ClickableIcon(
                        drawable = R.drawable.ic_play,
                        size = 40.dp,
                        clickAreaSize = 0.dp,
                        clickAreaShape = CircleShape
                    ) { }
                }
            }
        }

        if (showDivider) {
            Divider(color = Color.White)
        }
    }
}

@Preview
@Composable
private fun DemoItemViewPreview() {
    SynthLibTheme {
        DemoItemView(name = "Space Piano 1") {}
    }
}
