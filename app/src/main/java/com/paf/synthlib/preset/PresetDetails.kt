package com.paf.synthlib.preset


import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paf.synthlib.R
import com.paf.synthlib.common.views.AppButton
import com.paf.synthlib.common.views.AppTextField
import com.paf.synthlib.domain.Preset
import com.paf.synthlib.ui.theme.LightGrey
import com.paf.synthlib.ui.theme.SynthLibTheme
import com.paf.synthlib.utils.PreviewData.PresetPreviewData

@Composable
fun PresetDetailsScreen(preset: Preset?, onClickImage: (Bitmap) -> Unit) {

    val vm: PresetDetailsVm = viewModel()

    LaunchedEffect(preset) {
        vm.init(preset)
    }

    vm.apply {
        this.preset?.let {
            PresetDetailsView(
                it,
                vm.imageList,
                onNameChange = ::updatePresetName,
                onClickAddImage = {},
                onClickImage = onClickImage
            )
        }
    }
}

@Composable
fun PresetDetailsView(
    preset: Preset,
    imageList: List<Bitmap>,
    onNameChange: (String) -> Unit,
    onClickAddImage: () -> Unit,
    onClickImage: (Bitmap) -> Unit
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AppTextField(
            value = preset.name,
            onTextChanged = onNameChange,
            modifier = Modifier.padding(16.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 26.sp,
                textDecoration = TextDecoration.None,
                textAlign = TextAlign.Center
            ),
            showUnderline = false
        )

        if (imageList.isEmpty()) {
            AddButton(modifier = Modifier
                .size(250.dp)
                .padding(16.dp), onClickAddImage)
        } else {
            ImageListView(imageList = imageList, onClickImage = onClickImage)
        }

        AppButton(
            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
            text = "Add a picture",
            onClick = onClickAddImage
        )
    }
}

@Preview(name = "No image")
@Composable
private fun PresetDetailsNoImagePreview() {
    SynthLibTheme {
        PresetDetailsView(
            preset = PresetPreviewData.preset1,
            onNameChange = {},
            imageList = listOf(),
            onClickAddImage = {},
            onClickImage = {})
    }
}

@Preview(name = "With Images")
@Composable
private fun PresetDetailsWithImagePreview() {
    SynthLibTheme {
        PresetDetailsView(
            preset = PresetPreviewData.preset2,
            imageList = listOf(),
            onNameChange = {},
            onClickAddImage = {},
            onClickImage = {})
    }
}

@Composable
fun ImageListView(imageList: List<Bitmap>, onClickImage: (Bitmap) -> Unit) {
    LazyRow() {
        itemsIndexed(imageList) { i, image ->
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = LightGrey)
                    .clickable { onClickImage(image) }
            ) {

                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        start = if (i == 0) 16.dp else 8.dp,
                        end = if (i == imageList.lastIndex) 16.dp else 8.dp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageListPreview() {
    ImageListView(imageList = listOf(), onClickImage = {})
}