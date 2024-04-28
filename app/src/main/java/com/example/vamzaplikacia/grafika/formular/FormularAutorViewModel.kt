package com.example.vamzaplikacia.grafika.formular

import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.logika.FormularAutorUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularAutorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FormularAutorUIState())
    val uiState: StateFlow<FormularAutorUIState> = _uiState.asStateFlow()

    fun resetFormular() {
        _uiState.value = FormularAutorUIState()
    }

    fun setMeno(meno: String) {
        _uiState.update { currentState ->
            currentState.copy(menoAutora = meno)
        }
    }

    fun setPopis(popis: String) {
        _uiState.update { currentState ->
            currentState.copy(popis = popis)
        }
    }

    fun setNarodenie(narodenie: String) {
        _uiState.update { currentState ->
            currentState.copy(datumNar = narodenie)
        }
    }

    fun setUmrtie(umrtie: String) {
        _uiState.update { currentState ->
            currentState.copy(datumUmrtia = umrtie)
        }
    }

    fun setObrazok(cestaObrazok: String) {
        _uiState.update { currentState ->
            currentState.copy(obrazok = cestaObrazok)
        }
    }
}