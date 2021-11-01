package com.paf.synthlib.preset

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paf.synthlib.domain.Preset
import java.util.*
import kotlin.random.Random

class PresetDetailsVm: ViewModel() {

    var preset: Preset? by mutableStateOf(null)

    var imageList: List<Bitmap> by mutableStateOf(listOf())

    fun init(inputPreset: Preset?) {
        preset = inputPreset ?: getNewPreset()
    }

    fun updatePresetName(name: String) {
        if(name.isNotBlank()) {
            preset = preset?.copy(name = name)
        }
    }

    private fun getNewPreset() = Preset(
        id = UUID.randomUUID().toString(),
        name = "Preset" + Random.nextInt(999999999),
        hasDemo = false,
        tagList = listOf()
    )
}