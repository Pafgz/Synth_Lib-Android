package com.paf.synthlib.implementation.database.preset

import androidx.room.*
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.implementation.database.preset.model.PresetEntity
import com.paf.synthlib.implementation.database.preset.model.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Transaction
    @Query("select * from tag_table ORDER BY id DESC")
    fun getAllTags(): Flow<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tag_table WHERE id = :id ORDER BY id DESC")
    fun getPreset(id: Long) : Flow<PresetEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPreset(preset: PresetEntity): Long

    @Query("UPDATE tag_table SET name =:updateName where id=:id")
    suspend fun updatePreset(id: Long, updateName: String)

    @Query("DELETE FROM tag_table WHERE id = :id")
    suspend fun deletePreset(id: Long)
}