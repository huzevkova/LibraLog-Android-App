package com.example.vamzaplikacia.grafika.formular

import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularKnihyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FormularKnihyUIState())
    val uiState: StateFlow<FormularKnihyUIState> = _uiState.asStateFlow()

    fun resetFormular() {
        _uiState.value = FormularKnihyUIState()
    }

    fun setNazov(nazovKnihy: String) {
        _uiState.update { currentState ->
            currentState.copy(nazov = nazovKnihy)
        }
    }

    fun setAutor(autor: String) {
        _uiState.update { currentState ->
            currentState.copy(autor = autor)
        }
    }

    fun setRok(rok: String) {
        _uiState.update { currentState ->
            currentState.copy(rok = rok)
        }
    }

    fun setVydavatelstvo(vydavatelstvo: String) {
        _uiState.update { currentState ->
            currentState.copy(vydavatelstvo = vydavatelstvo)
        }
    }

    fun setPopis(popis: String) {
        _uiState.update { currentState ->
            currentState.copy(popis = popis)
        }
    }

    fun setPoznamky(poznamky: String) {
        _uiState.update { currentState ->
            currentState.copy(poznamky = poznamky)
        }
    }

    fun setPrecitana() {
        _uiState.update { currentState ->
            currentState.copy(precitana = !currentState.precitana)
        }
    }

    fun setNaNeskor() {
        _uiState.update { currentState ->
            currentState.copy(naNeskor = !currentState.naNeskor)
        }
    }

    fun setPozicana() {
        _uiState.update { currentState ->
            currentState.copy(pozicana = !currentState.pozicana)
        }
    }

    fun setKupena() {
        _uiState.update { currentState ->
            currentState.copy(kupena = !currentState.kupena)
        }
    }

    fun setPocetStran(pocetStran: String) {
        _uiState.update { currentState ->
            currentState.copy(pocetStran = pocetStran)
        }
    }

    fun setPocetPrecitanych(pocetPrecitanych: String) {
        _uiState.update { currentState ->
            currentState.copy(pocetPrecitanych = pocetPrecitanych)
        }
    }

    fun setHodnotenie(hodnotenie: String) {
        _uiState.update { currentState ->
            currentState.copy(hodnotenie = hodnotenie)
        }
    }

    fun setZanreVyber(zanreVyber: MutableList<Boolean>) {
        _uiState.update { currentState ->
            currentState.copy(zanreVyber = zanreVyber)
        }
    }

    fun setVlastnostiVyber(vlastnostiVyber: MutableList<Boolean>) {
        _uiState.update { currentState ->
            currentState.copy(vlastnostiVyber = vlastnostiVyber)
        }
    }
}