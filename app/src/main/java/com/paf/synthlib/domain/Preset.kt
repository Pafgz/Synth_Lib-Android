package com.paf.synthlib.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Preset(
    val id: String,
    val name: String,
    val hasDemo: Boolean,
    val tagList: List<Tag>
): Parcelable