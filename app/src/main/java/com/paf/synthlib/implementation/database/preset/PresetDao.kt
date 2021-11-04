package com.paf.synthlib.implementation.database.preset

import androidx.room.*
import com.paf.synthlib.implementation.database.preset.model.PresetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {

    @Transaction
    @Query("SELECT * from preset_table ORDER BY id DESC")
    fun getAllPresets(): Flow<List<PresetEntity>>

    @Transaction
    @Query("SELECT * FROM preset_table WHERE id = :id ORDER BY id DESC")
    fun getPreset(id: Long) : Flow<PresetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPreset(preset: PresetEntity): Long

    @Update
    suspend fun updatePreset(preset: PresetEntity)

    @Query("DELETE FROM preset_table WHERE id = :id")
    suspend fun deletePreset(id: Long)
}