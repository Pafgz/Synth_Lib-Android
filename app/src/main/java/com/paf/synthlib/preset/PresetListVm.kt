package com.paf.synthlib.preset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.paf.synthlib.domain.Preset

class PresetListVm: ViewModel() {

    val presetList by mutableStateOf<List<Preset>>(listOf())
}