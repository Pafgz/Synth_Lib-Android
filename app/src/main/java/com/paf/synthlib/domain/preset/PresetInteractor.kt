package com.paf.synthlib.domain.preset

import android.net.Uri
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.Flow

interface PresetInteractor {

    suspend fun savePreset(preset: Preset)

    suspend fun getAllPresets(): Flow<List<Preset>>

    suspend fun getPreset(id: Int): Flow<Preset>

    suspend fun updatePreset(preset: Preset)

    suspend fun deletePreset(preset: Preset)
}