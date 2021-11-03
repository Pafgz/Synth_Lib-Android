package com.paf.synthlib.implementation.database.preset.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paf.synthlib.domain.preset.model.Preset
import com.paf.synthlib.domain.preset.model.Tag

@Entity(tableName = "tag_table")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

val Tag.asEntity: TagEntity
    get() = TagEntity(name = name)

val TagEntity.asModel: Tag
    get() = Tag(id = id, name = name)