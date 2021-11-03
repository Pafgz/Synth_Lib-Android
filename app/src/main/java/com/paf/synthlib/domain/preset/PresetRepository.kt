package com.paf.synthlib.domain.preset

import android.net.Uri
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.Flow

interface PresetRepository {
    suspend fun createNewPreset(preset: Preset): Long

    suspend fun getAllPresets(): Flow<List<Preset>>

    suspend fun getPreset(id: Int): Flow<Preset>

    suspend fun updatePreset(preset: Preset)

    suspend fun deletePreset(preset: Preset)

    suspend fun saveImagesToLocalStorage(imageList: List<Uri>)

    suspend fun deleteImages(imageList: List<Uri>)
}