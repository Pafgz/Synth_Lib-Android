package com.paf.synthlib.preset

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paf.synthlib.R
import com.paf.synthlib.common.views.ClickableIcon
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.ui.theme.Blue
import com.paf.synthlib.ui.theme.Grey
import com.paf.synthlib.ui.theme.LightGrey
import com.paf.synthlib.ui.theme.SynthLibTheme
import com.paf.synthlib.utils.PreviewData.PresetPreviewData

@Composable
fun PresetItemView(
    preset: Preset,
    showDivider: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: (Preset) -> Unit
) {
    Column(modifier = modifier.background(Grey).clickable { onClick(preset) }) {
        Row(
            modifier = Modifier
                .height(70.dp)
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = preset.name,
                color = White,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 1
            )

            Row(modifier = Modifier
                .weight(1f, false)
                .wrapContentWidth()) {
                if (preset.hasDemo) {
                    ClickableIcon(
                        drawable = R.drawable.ic_play,
                        size = 40.dp,
                        clickAreaSize = 0.dp,
                        clickAreaShape = CircleShape
                    ) { }
                }
            }
        }
    }

    if (showDivider) {
        Divider(color = LightGrey)
    }
}

@Preview
@Composable
private fun PresetItem1ViewPreview() {
    SynthLibTheme {
        PresetItemView(preset = PresetPreviewData.preset1) {}
    }
}

@Preview
@Composable
private fun PresetItem2ViewPreview() {
    SynthLibTheme {
        PresetItemView(preset = PresetPreviewData.preset2, showDivider = false) {}
    }
}