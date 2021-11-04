package com.paf.synthlib.domain.preset.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val id: Long,
    val name: String
): Parcelable