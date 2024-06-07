package com.example.vamzaplikacia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.vamzaplikacia.data.AppDataContainer
import com.example.vamzaplikacia.grafika.zoznamiUI.kniznica
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private var startApp = mutableListOf(true, true, true)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var container = AppDataContainer(this)

        val knihy: Flow<List<Kniha>> = container.knihyRepository.getAllItemsStream()
        val autori: Flow<List<Autor>> = container.autoriRepository.getAllItemsStream()
        val policky: Flow<List<PolickaKniznice>> = container.polickyRepository.getAllItemsStream()

        lifecycleScope.launch {
            policky.collect {polickyList ->
                polickyList.forEach { policka ->
                    if (startApp[0]) {
                        kniznica.pridajZoznam(ZoznamKnih(policka.nazov))
                    }
                }
                startApp[0] = false
            }
        }
        lifecycleScope.launch {
            knihy.collect { knihyList ->
                knihyList.forEach { kniha ->
                    if (startApp[1]) {
                        kniznica.getZoznamVsetkych().pridajKnihu(kniha)
                    }
                }
                startApp[1] = false
            }
        }
        lifecycleScope.launch {
            autori.collect { autoriList ->
                autoriList.forEach { autor ->
                    if (startApp[2]) {
                        kniznica.pridajAutora(autor)
                        autor.nastavKnihy(kniznica.getZoznamVsetkych())
                    }
                }
                startApp[2] = false
            }
        }

        setContent {
            VAMZAplikaciaTheme {
                MainScreen(container)
            }
        }
    }
}
