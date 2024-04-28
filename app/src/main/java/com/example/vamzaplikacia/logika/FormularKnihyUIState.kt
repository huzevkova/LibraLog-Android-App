package com.example.vamzaplikacia.logika

data class FormularKnihyUIState (
    val nazov: String = "",
    val autor: String = "",
    val rok: String = "",
    val vydavatelstvo: String = "",
    val popis: String = "",
    val poznamky: String = "",
    val precitana: Boolean = false,
    val naNeskor: Boolean = false,
    val pozicana: Boolean = false,
    val kupena: Boolean = false,
    val pocetStran: String = "",
    val pocetPrecitanych: String = "",
    val hodnotenie: String = "",
    val zanreVyber: MutableList<Boolean> = mutableListOf(),
    val vlastnostiVyber: MutableList<Boolean> = mutableListOf()
)