package com.paf.synthlib.domain.preset

import android.net.Uri
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.Flow

interface PresetInteractor {

    suspend fun savePreset(preset: Preset, newImages: List<Uri>): Long

    suspend fun getAllPresets(): Flow<List<Preset>>

    suspend fun getPreset(id: Long): Flow<Preset>

    suspend fun updatePreset(preset: Preset, newImages: List<Uri>)

    suspend fun deletePreset(preset: Preset)

    suspend fun saveImagesToLocalStorage(presetId: Long, imageList: List<Uri>)

    suspend fun saveImageToLocalStorage(presetId: Long, image: Uri)

    suspend fun deleteImages(presetId: Long, imageList: List<Uri>)
}