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
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.ui.theme.SynthLibTheme
import com.paf.synthlib.utils.PreviewData.PresetPreviewData

@Composable
fun PresetItemView(preset: Preset, showDivider: Boolean = true, modifier: Modifier = Modifier, onClick: (Preset) -> Unit) {
    Column {
        Surface(modifier = modifier.height(70.dp).fillMaxSize()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = preset.name, fontSize = 20.sp, modifier = Modifier.weight(1f))

                Row(modifier = Modifier.weight(1f, false).wrapContentWidth()) {
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

        if(showDivider) {
            Divider(color = Color.White)
        }
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