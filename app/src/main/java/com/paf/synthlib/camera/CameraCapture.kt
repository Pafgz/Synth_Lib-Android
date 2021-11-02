package com.paf.synthlib.camera


import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.paf.synthlib.R
import com.paf.synthlib.common.views.ClickableIcon
import com.paf.synthlib.common.views.Permission
import com.paf.synthlib.ui.theme.Orange
import com.paf.synthlib.utils.camera.executor
import com.paf.synthlib.utils.camera.getCameraProvider
import com.paf.synthlib.utils.camera.takePicture
import kotlinx.coroutines.launch
import java.io.File

@ExperimentalPermissionsApi
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onClose: () -> Unit = {},
    onImageFile: (File) -> Unit = { }
) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "In order to take pictures, you need to give your permission to use the camera to the app",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("Can't open the camera, check the permissions in the settings")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        Box(modifier = modifier) {
            val lifecycleOwner = LocalLifecycleOwner.current
            val coroutineScope = rememberCoroutineScope()
            var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }
            Box {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onUseCase = {
                        previewUseCase = it
                    }
                )

                Box(modifier.align(Alignment.TopStart).padding(16.dp)) {
                    ClickableIcon(
                        R.drawable.ic_cross,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape),
                        clickAreaSize = 4.dp
                    ) { onClose() }
                }

                CameraButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                    onClick = {
                        coroutineScope.launch {
                            onImageFile(imageCaptureUseCase.takePicture(context.executor))
                        }
                    }
                )
            }

            LaunchedEffect(previewUseCase) {
                val cameraProvider = context.getCameraProvider()
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                    )
                } catch (ex: Exception) {
                    Log.e("CameraCapture", "Failed to bind camera use cases", ex)
                }
            }
        }
    }
}


@ExperimentalPermissionsApi
@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun CameraCapturePreview() {
    CameraCapture {}
}

@Composable
fun CameraButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .border(color = Color.Transparent, width = 0.dp, shape = CircleShape)
            .border(color = Orange, width = 1.dp, shape = CircleShape)
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(color = Orange)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun CameraButtonPreview() {
    CameraButton {}
}