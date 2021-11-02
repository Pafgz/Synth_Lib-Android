package com.paf.synthlib.preset

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paf.synthlib.R
import com.paf.synthlib.domain.Preset
import com.paf.synthlib.ui.theme.LightGrey
import com.paf.synthlib.ui.theme.Orange
import com.paf.synthlib.ui.theme.SynthLibTheme
import com.paf.synthlib.utils.PreviewData.PresetPreviewData

@Composable
fun PresetListScreen(openPreset: (Preset?) -> Unit) {
    val vm: PresetListVm = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(vm.presetList.isEmpty()) { isEmpty ->
            if (isEmpty) {
                PresetListEmptyView {
                    openPreset(null)
                }
            } else {
                PresetListView(vm.presetList) {
                    openPreset(it)
                }
            }
        }
    }
}

@Composable
fun PresetListView(presetList: List<Preset>, onClickPreset: (Preset) -> Unit) {
    LazyColumn {
        itemsIndexed(presetList) { i, item ->
            val isLastItem = presetList.lastIndex == i
            PresetItemView(item, showDivider = !isLastItem) {
                onClickPreset(it)
            }
        }
    }
}

@Preview
@Composable
private fun PresetListPreview() {
    SynthLibTheme {
        PresetListView(PresetPreviewData.presetList) {}
    }
}


@Composable
fun PresetListEmptyView(modifier: Modifier = Modifier.fillMaxSize(), onClick: () -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("No Preset", modifier = Modifier.padding(16.dp), fontSize = 24.sp, color = Color.White)

        AddButton(onClick = onClick)
    }
}

@Preview
@Composable
private fun PresetListEmptyPreview() {
    SynthLibTheme {
        PresetListEmptyView {}
    }
}

@Composable
fun AddButton(modifier: Modifier = Modifier.size(230.dp), shape: Shape = RoundedCornerShape(16.dp), backgroundColor: Color = LightGrey, foregroundColor: Color = Color.White, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(shape = shape)
            .background(color = backgroundColor)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = null,
            tint = foregroundColor,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun AddButtonPreview() {
    SynthLibTheme {
        AddButton {}
    }
}

@Preview
@Composable
private fun AddButtonSmallCirclePreview() {
    SynthLibTheme {
        AddButton(
            modifier = Modifier
                .size(32.dp),
            shape = CircleShape,
            backgroundColor = Orange
        ) {}
    }
}