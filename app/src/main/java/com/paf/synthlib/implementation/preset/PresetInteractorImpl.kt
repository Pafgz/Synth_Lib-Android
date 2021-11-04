package com.paf.synthlib.implementation.preset

import android.net.Uri
import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.PresetRepository
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.Flow

class PresetInteractorImpl(private val presetRepository: PresetRepository): PresetInteractor {

    override suspend fun savePreset(preset: Preset, images: List<Uri>): Long {
        var id: Long
        presetRepository.apply {
            id = createNewPreset(preset)
            saveImagesToLocalStorage(preset.id, images)
            deleteImages(preset.id, listOf())
        }
        return id
    }

    override suspend fun updatePreset(preset: Preset, newImages: List<Uri>) {
        presetRepository.apply {
            updatePreset(preset)
            saveImagesToLocalStorage(preset.id, newImages)
        }
    }

    override suspend fun getAllPresets(): Flow<List<Preset>> = presetRepository.getAllPresets()

    override suspend fun getPreset(id: Long): Flow<Preset> = presetRepository.getPreset(id)

    override suspend fun deletePreset(preset: Preset) = presetRepository.deletePreset(preset)
}