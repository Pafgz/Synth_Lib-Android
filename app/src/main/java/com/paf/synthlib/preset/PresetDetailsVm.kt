package com.paf.synthlib.preset

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.paf.synthlib.domain.Preset
import java.io.File
import java.util.*
import kotlin.random.Random

class PresetDetailsVm: ViewModel() {

    var preset: Preset? by mutableStateOf(null)

    var imageList = mutableListOf<Image>()

    fun init(inputPreset: Preset?) {
        preset = inputPreset ?: getNewPreset()
    }

    fun updatePresetName(name: String) {
        if(name.isNotBlank()) {
            preset = preset?.copy(name = name)
        }
    }

    fun addNewPicture(imageUri: Uri) {
        imageList.add(Image(imageUri))
    }

    fun addNewPictures(uriList: List<Uri>) {
        imageList.addAll(
            uriList.map { Image(it) }
        )
    }

    private fun getNewPreset() = Preset(
        id = UUID.randomUUID().toString(),
        name = "Preset" + Random.nextInt(999999999),
        hasDemo = false,
        tagList = listOf()
    )

    data class Image(
        val uri: Uri,
        val isSaved: Boolean = false
    )
}