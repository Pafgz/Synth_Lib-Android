package com.paf.synthlib.preset

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import kotlin.random.Random

class PresetDetailsVm : ViewModel(), KoinComponent {

    private val presetInteractor: PresetInteractor by inject()

    var preset: Preset? by mutableStateOf(null)

    var imageList = mutableListOf<ImageWrapper>()

    var hasChanges by mutableStateOf(false)

    var isNewPreset = true

    fun init(inputPreset: Preset?) {
        preset = inputPreset?.let {
            isNewPreset = false
            it
        } ?: getNewPreset()
    }

    fun save() {
        preset?.let {
            viewModelScope.launch {
                if(isNewPreset) {
                    presetInteractor.savePreset(it)
                } else {
                    presetInteractor.updatePreset(it)
                }

                isNewPreset = false
            }
        }
    }

    fun updatePresetName(newName: String) {
        if (newName.isNotBlank()) {
            preset = preset?.copy(name = newName)
            hasChanges = true
        }
    }

    fun addNewPicture(imageUri: Uri) {
        imageList.add(ImageWrapper(imageUri))
        hasChanges = true
    }

    fun addNewPictures(uriList: List<Uri>) {
        imageList.addAll(
            uriList.map { ImageWrapper(it) }
        )
        hasChanges = true
    }

    private fun getNewPreset() = Preset(
        id = 0,
        name = "Preset" + Random.nextInt(999999),
        hasDemo = false
    )

    data class ImageWrapper(
        val uri: Uri,
        val isSaved: Boolean = false
    )
}