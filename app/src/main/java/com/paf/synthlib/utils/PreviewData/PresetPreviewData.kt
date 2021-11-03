package com.paf.synthlib.utils.PreviewData

import com.paf.synthlib.domain.preset.model.Preset

object PresetPreviewData {

    val preset1 = Preset(
        id = 45678,
        name = "Starry Pad",
        hasDemo = false,
        tagList = listOf(),
        imageList = listOf()
    )
    val preset2 = Preset(
        id = 543876,
        name = "Epic Riser Delayed",
        hasDemo = true,
        tagList = listOf(),
        imageList = listOf()
    )
    val preset3 = Preset(
        id = 3468795,
        name = "Pluck",
        hasDemo = true,
        tagList = listOf(),
        imageList = listOf()
    )
    val preset4 = Preset(
        id = 907534,
        name = "Juno Lead",
        hasDemo = true,
        tagList = listOf(),
        imageList = listOf()
    )
    val preset5 = Preset(
        id = 8679345,
        name = "Techno Beat Bass",
        hasDemo = false,
        tagList = listOf(),
        imageList = listOf()
    )

    val presetList = listOf(preset1, preset2, preset3, preset4, preset5)
}