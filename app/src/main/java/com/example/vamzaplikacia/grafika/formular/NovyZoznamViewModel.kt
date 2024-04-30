package com.example.vamzaplikacia.grafika.formular

import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import com.example.vamzaplikacia.logika.NovyZoznamUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NovyZoznamViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NovyZoznamUIState())
    val uiState: StateFlow<NovyZoznamUIState> = _uiState.asStateFlow()

    fun setNazov(nazov: String) {
        _uiState.update { currentState ->
            currentState.copy(nazov = nazov)
        }
    }

    fun setObrazov(cesta: String) {
        _uiState.update { currentState ->
            currentState.copy(obrazok = cesta)
        }
    }

    fun resetFormular() {
        _uiState.value = NovyZoznamUIState()
    }


    fun openDialog() {
        _uiState.value = _uiState.value.copy(showDialog = true)
    }

    fun dismissDialog() {
        _uiState.value = _uiState.value.copy(showDialog = false)
    }
}