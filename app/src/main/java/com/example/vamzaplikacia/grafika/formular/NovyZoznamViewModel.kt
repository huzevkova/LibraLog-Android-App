package com.example.vamzaplikacia.grafika.formular

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.data.polickyData.PolickyRepository
import com.example.vamzaplikacia.grafika.UIState.NovyZoznamUIState
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel pre dialóg nového zoznamu
 *
 * @param polickyRepository repozitár poličiek / zoznamov
 */
class NovyZoznamViewModel(private val polickyRepository: PolickyRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(NovyZoznamUIState())
    val uiState: StateFlow<NovyZoznamUIState> = _uiState.asStateFlow()

    fun setNazov(nazov: String) {
        _uiState.update { currentState ->
            currentState.copy(nazov = nazov)
        }
    }

    fun setObrazok(cesta: Uri) {
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

    suspend fun saveZoznam(zoznam: ZoznamKnih) {
        polickyRepository.insertItem(PolickaKniznice(nazov = zoznam.getNazov()))
    }


}