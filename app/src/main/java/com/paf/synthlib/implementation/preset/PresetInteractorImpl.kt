package com.paf.synthlib.implementation.preset

import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.PresetRepository
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.Flow

class PresetInteractorImpl(private val presetRepository: PresetRepository): PresetInteractor {

    override suspend fun savePreset(preset: Preset) {
        presetRepository.apply {
            saveImagesToLocalStorage(preset.imageList)
            createNewPreset(preset)
        }
    }

    override suspend fun updatePreset(preset: Preset) {
        presetRepository.apply {
            updatePreset(preset)
        }
    }

    override suspend fun getAllPresets(): Flow<List<Preset>> = presetRepository.getAllPresets()

    override suspend fun getPreset(id: Int): Flow<Preset> = presetRepository.getPreset(id)

    override suspend fun deletePreset(preset: Preset) = presetRepository.deletePreset(preset)
}