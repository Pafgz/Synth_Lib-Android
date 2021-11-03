package com.paf.synthlib.preset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paf.synthlib.domain.preset.PresetInteractor
import com.paf.synthlib.domain.preset.model.Preset
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PresetListVm : ViewModel(), KoinComponent {

    private val presetInteractor: PresetInteractor by inject()

    val presetList = mutableListOf<Preset>()

    init {
        viewModelScope.launch {
            presetInteractor.getAllPresets().collect {
                presetList.addAll(it)
            }
        }
    }
}