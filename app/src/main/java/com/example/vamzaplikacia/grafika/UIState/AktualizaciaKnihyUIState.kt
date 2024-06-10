package com.example.vamzaplikacia.grafika.UIState

/**
 * UI state pre aktualizáciu knihy
 */
data class AktualizaciaKnihyUIState (
    val hodnotenie: Double = 0.0,
    val pocetPrecitanych: Int = 0,
    val zmena: Boolean =  false
)