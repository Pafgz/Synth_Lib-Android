package com.paf.synthlib.domain

data class Preset(
    val id: String,
    val name: String,
    val hasDemo: Boolean,
    val tagList: List<Tag>
)