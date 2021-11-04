package com.paf.synthlib.preset

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

class PresetDetailsVm : ViewModel(), KoinComponent {

    private val presetInteractor: PresetInteractor by inject()

    var preset: Preset? by mutableStateOf(null)

    var imageList = mutableListOf<ImageWrapper>()

    var hasChanges by mutableStateOf(false)

    private var isNewPreset = true

    var isLoading by mutableStateOf(true)

    fun init(presetId: Long) {
        isLoading = true
        if (presetId >= 0) {
            isNewPreset = false
            getPreset(presetId)
        } else {
            preset = getNewPreset()
            isLoading = false
        }
    }

    fun save() {
        preset?.let { presetToSave ->
            isLoading = true
            viewModelScope.launch {
                if (isNewPreset) {
                    val presetId = presetInteractor.savePreset(presetToSave, getUnsavedImageUris())
                    isNewPreset = false
                    hasChanges = false
                    presetInteractor.getPreset(presetId).collect {
                        preset = it
                    }
                } else {
                    presetInteractor.updatePreset(presetToSave, getUnsavedImageUris())
                    hasChanges = false
                    isLoading = false
                }
            }
        }
    }

    fun updatePresetName(newName: String) {
        preset = preset?.copy(name = newName)
        hasChanges = newName.isNotBlank()
    }

    fun addNewPicture(imageUri: Uri) {
        imageList.add(ImageWrapper(imageUri))
        viewModelScope.launch {
            preset?.id?.let { id ->
                isLoading = true
                presetInteractor.saveImageToLocalStorage(id, imageUri)
                isLoading = false
            }
        }
    }

    fun addNewPictures(uriList: List<Uri>) {
        imageList.addAll(
            uriList.map { ImageWrapper(it) }
        )
        viewModelScope.launch {
            preset?.id?.let { id ->
                isLoading = true
                presetInteractor.saveImagesToLocalStorage(id, uriList)
                isLoading = false
            }
        }
    }

    private fun getPreset(presetId: Long) {
        viewModelScope.launch {
            presetInteractor.getPreset(presetId).collect {
                preset = it
                isLoading = false
            }
        }
    }

    private fun getNewPreset() = Preset(
        id = 0,
        name = "Preset" + Random.nextInt(999999),
        hasDemo = false
    )

    private fun getUnsavedImageUris() = imageList.filter { !it.isSaved }.map { it.uri }

    data class ImageWrapper(
        val uri: Uri,
        val isSaved: Boolean = false
    )
}