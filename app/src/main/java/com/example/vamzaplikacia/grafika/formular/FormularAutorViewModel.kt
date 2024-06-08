package com.example.vamzaplikacia.grafika.formular

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.data.autoriData.AutoriRepository
import com.example.vamzaplikacia.grafika.UIState.FormularAutorUIState
import com.example.vamzaplikacia.logika.knihy.Autor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularAutorViewModel(private val autoriRepository: AutoriRepository) : ViewModel() {

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

    fun setObrazok(cestaObrazok: Uri) {
        _uiState.update { currentState ->
            currentState.copy(obrazok = cestaObrazok)
        }
    }

    suspend fun saveAutora(autor: Autor) {
        autoriRepository.insertItem(autor)
    }
}