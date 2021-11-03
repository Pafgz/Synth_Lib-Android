package com.paf.synthlib.implementation.preset

import android.net.Uri
import com.paf.synthlib.domain.preset.PresetRepository
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.implementation.database.preset.PresetDao
import com.paf.synthlib.implementation.database.preset.model.asEntity
import com.paf.synthlib.implementation.database.preset.model.asModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class PresetRepositoryImpl(private val presetDao: PresetDao): PresetRepository {

    override suspend fun createNewPreset(preset: Preset): Long =
        presetDao.addPreset(preset.asEntity.copy(id = 0))

    override suspend fun getAllPresets(): Flow<List<Preset>> =
        presetDao.getAllPresets().map {
            it.map { presetEntity ->  presetEntity.asModel }
        }

    override suspend fun getPreset(id: Int): Flow<Preset> = presetDao.getPreset(id).map { it.asModel }

    override suspend fun updatePreset(preset: Preset) = presetDao.updatePreset(preset.asEntity)

    override suspend fun deletePreset(preset: Preset) = presetDao.deletePreset(preset.id)

    override suspend fun saveImagesToLocalStorage(imageList: List<Uri>) {

    }

    override suspend fun deleteImages(imageList: List<Uri>) {

    }
}