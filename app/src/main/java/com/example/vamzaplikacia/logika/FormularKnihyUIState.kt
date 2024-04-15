package com.example.vamzaplikacia.logika

data class FormularKnihyUIState (
    val nazov: String = "",
    val autor: String = "",
    val rok: Int = 0,
    val vydavatelstvo: String = "",
    val popis: String = "",
    val poznamky: String = "",
    val precitana: Boolean = false,
    val naNeskor: Boolean = false,
    val pozicana: Boolean = false,
    val kupena: Boolean = false,
    val pocetStran: Int = 0,
    val pocetPrecitanych: Int = -1,
    val hodnotenie: Int = -1,
    val zanreVyber: MutableList<Boolean> = mutableListOf<Boolean>(),
    val vlastnostiVyber: MutableList<Boolean> = mutableListOf<Boolean>()
)