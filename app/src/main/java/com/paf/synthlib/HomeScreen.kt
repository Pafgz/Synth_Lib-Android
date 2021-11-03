package com.paf.synthlib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paf.synthlib.common.views.ClickableIcon
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.preset.PresetListScreen
import com.paf.synthlib.ui.theme.SynthLibTheme

@Composable
fun HomeScreen(onClickPreset: (Preset?) -> Unit) {
    Scaffold(
        topBar = {
            HomeTopBar(onClickNewPreset = onClickPreset)
        }) {
        PresetListScreen(openPreset = onClickPreset)
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SynthLibTheme {
        HomeScreen {}
    }
}

@Composable
fun HomeTopBar(onClickNewPreset: (Preset?) -> Unit) {
    TopAppBar {
        Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f).fillMaxHeight()) {
            ClickableIcon(
                drawable = R.drawable.ic_add,
                onClick = { onClickNewPreset(null) },
                tint = Color.White,
                size = 32.dp,
                clickAreaSize = 4.dp,
            )
        }
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    SynthLibTheme {
        HomeTopBar {}
    }
}