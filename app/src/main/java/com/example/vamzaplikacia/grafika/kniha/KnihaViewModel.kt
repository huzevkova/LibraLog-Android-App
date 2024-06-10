package com.example.vamzaplikacia.grafika.kniha

import androidx.lifecycle.ViewModel
import com.example.vamzaplikacia.data.knihyData.KnihyRepository
import com.example.vamzaplikacia.grafika.UIState.AktualizaciaKnihyUIState
import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel pre knihu
 *
 * @param knihyRepository repozitár kníh
 */
class KnihaViewModel(private val knihyRepository: KnihyRepository) : ViewModel() {

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

    fun zmena() {
        _uiState.update { currentState ->
            currentState.copy(zmena = true)
        }
    }

    suspend fun updateKniha(kniha: Kniha) {
        if (_uiState.value.zmena) {
            knihyRepository.updateItem(kniha)
        }
    }
}