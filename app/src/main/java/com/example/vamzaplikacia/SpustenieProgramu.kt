package com.example.vamzaplikacia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamzaplikacia.data.AppContainer
import com.example.vamzaplikacia.grafika.formular.FormularAutorViewModel
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.formular.NovyZoznamViewModel
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.organizer.LibraApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Obrazovka predstavujúca načítanie s načítacím kolieskom
 */
@Composable
fun NacitanieScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.libralog),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}

private var start = true
private var _kniznica: Kniznica? = null

/**
 * Načíta dáta z databázy a spustí aplikáciu
 *
 * @param container kontajner s repozitármi
 * @param kniznica knižnica aplikácie
 */
@Composable
fun SpustenieProgramu(container: AppContainer, kniznica: Kniznica) {
    _kniznica = kniznica
    val viewModel = SpustenieViewModel(container)
    val isLoading by viewModel.nacitaSa.collectAsState()

    var showLoadingScreen by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1000)
        showLoadingScreen = false
    }

    if (start && (isLoading || showLoadingScreen)) {
        NacitanieScreen()
    } else {
        start = false
        LibraApp(
            viewModelFormular = FormularKnihyViewModel(container.knihyRepository),
            viewModelAutor = FormularAutorViewModel(container.autoriRepository),
            viewModelZoznam = NovyZoznamViewModel(container.polickyRepository),
            container = container,
            kniznica = _kniznica!!
        )
    }
}


/**
 * Pri spustení načíta dáta do knižnice
 *
 * @param container kontajner s repozitármi
 */
class SpustenieViewModel(private val container: AppContainer) : ViewModel() {
    private val _nacitaSa1 = MutableStateFlow(true)
    private val _nacitaSa2 = MutableStateFlow(true)
    private val _nacitaSa3 = MutableStateFlow(true)
    private val _nacitaSa = MutableStateFlow(_nacitaSa1.value || _nacitaSa2.value || _nacitaSa3.value)
    val nacitaSa: StateFlow<Boolean> get() = _nacitaSa

    init {
        nacitajData()
    }

    private fun nacitajData() {
        val knihy: Flow<List<Kniha>> = container.knihyRepository.getAllItemsStream()
        val autori: Flow<List<Autor>> = container.autoriRepository.getAllItemsStream()
        val policky: Flow<List<PolickaKniznice>> = container.polickyRepository.getAllItemsStream()

        if (_nacitaSa.value) {
            viewModelScope.launch {
                policky.collect { polickyList ->
                    polickyList.forEach { policka ->
                        if (_nacitaSa1.value) {
                            _kniznica!!.pridajZoznam(ZoznamKnih(policka.nazov, policka.obrazok))
                        }
                    }
                    _nacitaSa1.value = false
                    _nacitaSa.value = (_nacitaSa1.value || _nacitaSa2.value || _nacitaSa3.value)
                }

            }
            viewModelScope.launch {
                knihy.collect { knihyList ->
                    knihyList.forEach { kniha ->
                        if (_nacitaSa2.value) {
                            _kniznica!!.getZoznamVsetkych().pridajKnihu(kniha)
                            if (kniha.policka != _kniznica!!.getZoznamVsetkych().getNazov()) {
                                _kniznica!!.getZoznam(kniha.policka)?.pridajKnihu(kniha)
                            }
                        }
                    }
                    _nacitaSa2.value = false
                    _nacitaSa.value = (_nacitaSa1.value || _nacitaSa2.value || _nacitaSa3.value)
                }
            }
            viewModelScope.launch {
                autori.collect { autoriList ->
                    autoriList.forEach { autor ->
                        if (_nacitaSa3.value) {
                            autor.nastavKnihy(_kniznica!!.getZoznamVsetkych())
                            _kniznica!!.pridajAutora(autor)
                        }
                    }
                    _nacitaSa3.value = false
                    _nacitaSa.value = (_nacitaSa1.value || _nacitaSa2.value || _nacitaSa3.value)
                }
            }
        }
    }
}