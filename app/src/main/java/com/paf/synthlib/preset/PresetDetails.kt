package com.paf.synthlib.preset


import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.paf.synthlib.camera.CameraCapture
import com.paf.synthlib.common.views.AppButton
import com.paf.synthlib.common.views.AppTextField
import com.paf.synthlib.domain.Preset
import com.paf.synthlib.gallery.GallerySelect
import com.paf.synthlib.ui.theme.LightGrey
import com.paf.synthlib.ui.theme.Orange
import com.paf.synthlib.ui.theme.SynthLibTheme
import com.paf.synthlib.utils.PreviewData.PresetPreviewData
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun PresetDetailsScreen(preset: Preset?, onClickImage: (Uri) -> Unit, onClickCamera: () -> Unit) {

    val vm: PresetDetailsVm = viewModel()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var screenState by remember { mutableStateOf(PresetScreenState.Details) }

    LaunchedEffect(preset) {
        vm.init(preset)
    }

    Crossfade(screenState) {
        when(it) {
            PresetScreenState.Details -> ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetContent = {
                    PictureSelectionOptions(onClickGallery = {
                        coroutineScope.launch {
                            sheetState.hide()
                            screenState = PresetScreenState.Gallery
                        }

                    }, onClickCamera = {
                        coroutineScope.launch {
                            sheetState.hide()
                            screenState = PresetScreenState.Camera
                        }
                    })
                }) {
                vm.apply {
                    this.preset?.let { preset ->
                        PresetDetailsView(
                            preset = preset,
                            imageList = vm.imageList,
                            onNameChange = ::updatePresetName,
                            onClickAddImage = { coroutineScope.launch { sheetState.show() } },
                            onClickImage = onClickImage,
                            onClickAddDemo = {}
                        )
                    }
                }
            }
            PresetScreenState.Camera -> CameraCapture(
                onClose = { screenState = PresetScreenState.Details }) { file ->
                screenState = PresetScreenState.Details
                vm.addNewPicture(file.toUri())
            }
            PresetScreenState.Gallery -> GallerySelect { images ->
                screenState = PresetScreenState.Details
                images?.let { vm.addNewPictures(images) }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PresetDetailsView(
    preset: Preset,
    imageList: List<Uri>,
    onNameChange: (String) -> Unit,
    onClickAddImage: () -> Unit,
    onClickImage: (Uri) -> Unit,
    onClickAddDemo: () -> Unit
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally, ) {
        AppTextField(
            value = preset.name,
            onTextChanged = onNameChange,
            modifier = Modifier.padding(16.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 30.sp,
                textDecoration = TextDecoration.None,
                textAlign = TextAlign.Center
            ),
            showUnderline = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (imageList.isEmpty()) {
            AddButton(
                onClick = onClickAddImage
            )
        } else {
            ImageListView(imageList = imageList, onClickImage = onClickImage)
        }

        Spacer(Modifier.height(16.dp))

        AppButton(
            modifier = Modifier.width(230.dp),
            text = "Add a picture",
            onClick = onClickAddImage,
        )

        Spacer(Modifier.height(36.dp))

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                "Sound Demos",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                AddButton(
                    modifier = Modifier
                        .size(32.dp),
                    shape = CircleShape, backgroundColor = Orange
                ) { onClickAddDemo() }

                Spacer(Modifier.width(16.dp))
            }
        }

        Spacer(Modifier.height(16.dp))
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
            onClickImage = {},
            onClickAddDemo = {}
        )
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
            onClickImage = {},
            onClickAddDemo = {})
    }
}

@ExperimentalCoilApi
@Composable
fun ImageListView(imageList: List<Uri>, onClickImage: (Uri) -> Unit) {
    LazyRow {

        item {
            Spacer(Modifier.size(16.dp))
        }

        itemsIndexed(imageList) { i, image ->
            Box(
                modifier = Modifier
                    .padding(
                        start = if (i != 0) 16.dp else 0.dp
                    )
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = LightGrey)
                    .clickable { onClickImage(image) }
                    .size(230.dp)
            ) {

                Image(
                    painter = rememberImagePainter(image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        item {
            Spacer(Modifier.size(16.dp))
        }
    }
}

@Preview
@Composable
private fun ImageListPreview() {
    ImageListView(imageList = listOf(), onClickImage = {})
}

@Composable
fun PictureSelectionOptions(onClickGallery: () -> Unit, onClickCamera: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "Gallery",
            modifier = Modifier
                .clickable { onClickGallery() }
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.White,
            fontSize = 24.sp
        )
        Text(
            "Camera",
            modifier = Modifier
                .clickable { onClickCamera() }
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.White,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun PictureSelectionOptionsPreview() {
    SynthLibTheme {
        PictureSelectionOptions(onClickGallery = {}) { }
    }
}

private enum class PresetScreenState {
    Details,
    Camera,
    Gallery
}