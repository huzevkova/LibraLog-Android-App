package com.example.vamzaplikacia.organizer.pomocne_fun

import android.net.Uri
import com.example.vamzaplikacia.grafika.UIState.AktualizaciaKnihyUIState
import com.example.vamzaplikacia.grafika.UIState.FormularAutorUIState
import com.example.vamzaplikacia.grafika.UIState.FormularKnihyUIState
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.organizer.Premenne

/**
 * Pridanie zadanej knihy do knižnice
 *
 * @param uiState stav knihy
 * @param kniznica knižnica aplikácie
 */
fun pridajZadanuKnihu(uiState: FormularKnihyUIState, kniznica: Kniznica): Kniha {
    val zanre = mutableListOf<Zanre>()
    uiState.zanreVyber.forEachIndexed { index, b ->
        if (b) {
            zanre.add(Zanre.entries[index])
        }
    }
    val vlastnosti = mutableListOf<Vlastnosti>()
    uiState.vlastnostiVyber.forEachIndexed { index, b ->
        if (b) {
            vlastnosti.add(Vlastnosti.entries[index])
        }
    }

    val rok: Int = if(uiState.rok.toIntOrNull()==null) 0 else uiState.rok.toInt()
    val pocetStran: Int = if(uiState.pocetStran.toIntOrNull()==null) 0 else uiState.pocetStran.toInt()
    val pocetPrecitanych: Int = if(uiState.pocetPrecitanych.toIntOrNull()==null) 0 else uiState.pocetPrecitanych.toInt()
    val hodnotenie: Double = if(uiState.hodnotenie.toDoubleOrNull()==null) 0.0 else uiState.hodnotenie.toDouble()

    val kniha = Kniha(uiState.nazov, uiState.autor, rok, uiState.vydavatelstvo,
        uiState.obrazok, uiState.popis, uiState.poznamky, uiState.precitana, uiState.naNeskor,
        uiState.pozicana, uiState.kupena, pocetStran, pocetPrecitanych, hodnotenie, Premenne.zoznamKnih.getNazov())
    kniha.zanre = zanre
    kniha.vlastnosti = vlastnosti
    kniznica.getZoznamVsetkych().pridajKnihu(kniha)
    Premenne.zoznamKnih.pridajKnihu(kniha)
    return kniha
}

/**
 * Pridanie nového autora do knižnice
 *
 * @param uiState stav autora
 * @param kniznica knižnica aplikácie
 */
fun pridajZadanehoAutora(uiState: FormularAutorUIState, kniznica: Kniznica): Autor {
    val autor = Autor(uiState.menoAutora, uiState.datumNar, uiState.datumUmrtia, obrazokCesta = uiState.obrazok)
    autor.popis = uiState.popis
    autor.nastavKnihy(kniznica.getZoznamVsetkych())
    kniznica.getZoznamAutorov().pridajAutora(autor)
    return autor
}

/**
 * Aktualizácia knihy v knižnici
 *
 * @param kniha
 * @param uiState stav aktualizovanej knihy
 */
fun aktualizujKnihu(kniha: Kniha, uiState: AktualizaciaKnihyUIState) {
    kniha.hodnotenie = uiState.hodnotenie
    kniha.pocetPrecitanych = uiState.pocetPrecitanych

}

/**
 * Pridanie nového zoznamu do knižnice
 *
 * @param nazov
 * @param obrazok
 * @param kniznica
 */
fun pridajZoznam(nazov: String, obrazok: Uri? = null, kniznica: Kniznica): ZoznamKnih {
    var zoznam = ZoznamKnih(nazov, obrazok = obrazok)
    kniznica.getVsetkyZoznamy().add(zoznam)
    return zoznam
}