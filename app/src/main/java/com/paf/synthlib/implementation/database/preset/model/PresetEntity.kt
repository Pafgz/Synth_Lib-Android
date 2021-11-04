package com.paf.synthlib.implementation.database.preset.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paf.synthlib.domain.preset.model.Preset

@Entity(tableName = "preset_table")
data class PresetEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Long = 0,
    val name: String
)

val Preset.asEntity: PresetEntity
    get() = PresetEntity(
        id = id,
        name = name
    )

val PresetEntity.asModel: Preset
    get() = Preset(id = id, name = name, hasDemo = false, tagList = listOf())