package com.paf.synthlib.preset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var presetList by mutableStateOf<List<Preset>>(listOf())

    var state by mutableStateOf(PresetListState.Loading)

    init {
        viewModelScope.launch {
            state = PresetListState.Loading
            presetInteractor.getAllPresets().collect {
                presetList = it

                state = if(presetList.isEmpty()) PresetListState.Empty else PresetListState.List
            }
        }
    }

    enum class PresetListState {
        Empty, List, Loading
    }
}