package com.example.vamzaplikacia.grafika.kniha

import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.logika.AktualizaciaKnihyUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KnihaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AktualizaciaKnihyUIState())
    val uiState: StateFlow<AktualizaciaKnihyUIState> = _uiState.asStateFlow()

    fun setHodnotenie(hodnotenie: Double) {
        _uiState.update { currentState ->
            currentState.copy(hodnotenie = hodnotenie)
        }
    }

    fun setPocetPrecitanych(precitane: Int) {
        _uiState.update { currentState ->
            currentState.copy(pocetPrecitanych = precitane)
        }
    }
}