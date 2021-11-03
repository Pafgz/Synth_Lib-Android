package com.paf.synthlib.domain.preset.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Preset(
    val id: Int,
    val name: String,
    val hasDemo: Boolean,
    val tagList: List<Tag> = listOf(),
    val imageList: List<Uri> = listOf()
): Parcelable