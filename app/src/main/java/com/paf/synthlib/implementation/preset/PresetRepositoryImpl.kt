package com.paf.synthlib.implementation.preset

import android.net.Uri
import com.paf.synthlib.domain.preset.PresetRepository
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.implementation.database.preset.PresetDao
import com.paf.synthlib.implementation.database.preset.model.asEntity
import com.paf.synthlib.implementation.database.preset.model.asModel
import com.paf.synthlib.utils.FileManager
import com.paf.synthlib.utils.getBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PresetRepositoryImpl(
    private val presetDao: PresetDao,
    private val fileManager: FileManager,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PresetRepository {

    override suspend fun createNewPreset(preset: Preset): Long = withContext(defaultDispatcher) {
        presetDao.addPreset(preset.asEntity.copy(id = 0))
    }

    override suspend fun getAllPresets(): Flow<List<Preset>> = withContext(defaultDispatcher) {
        presetDao.getAllPresets().map {
            it.map { presetEntity -> presetEntity.asModel }
        }
    }

    override suspend fun getPreset(id: Long): Flow<Preset> = withContext(defaultDispatcher) {
        presetDao.getPreset(id).map { it.asModel }
    }

    override suspend fun updatePreset(preset: Preset) =
        withContext(defaultDispatcher) { presetDao.updatePreset(preset.asEntity) }

    override suspend fun deletePreset(preset: Preset) =
        withContext(defaultDispatcher) { presetDao.deletePreset(preset.id) }

    override suspend fun saveImageToLocalStorage(presetId: Long, image: Uri): Unit =
        withContext(defaultDispatcher) {
            fileManager.saveImage(PresetFolderName + "_$presetId", image)
        }

    override suspend fun deleteImages(presetId: Long, image: Uri) =
        withContext(defaultDispatcher) {

        }

    companion object {
        private const val PresetFolderName = "Preset"
    }
}