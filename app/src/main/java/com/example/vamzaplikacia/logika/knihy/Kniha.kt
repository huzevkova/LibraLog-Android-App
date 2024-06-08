package com.example.vamzaplikacia.logika.knihy

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import java.util.Calendar

/**
 * Trieda pre knihu, ukľadjúca si všetky informácie o knihe, zároveň tabuľka
 *
 * @param nazov
 * @param autor
 * @param rokVydania
 * @param vydavatelstvo
 * @param obrazok
 * @param popis
 * @param poznamky
 * @param precitana
 * @param naNeskor
 * @param pozicana
 * @param kupena
 * @param pocetStran
 * @param pocetPrecitanych
 * @param hodnotenie
 * @param policka
 * @param datumPridania aktuálny dátum
 * @param id_knihy
 */
@Entity(tableName = "knihy")
data class Kniha(
    val nazov: String,
    val autor: String,
    val rokVydania: Int,
    val vydavatelstvo: String = "Neznáme",
    val obrazok: Uri? = null,
    var popis: String = "Bez popisu.",
    var poznamky: String = "Bez poznámok.",
    var precitana: Boolean = false,
    var naNeskor: Boolean = false,
    var pozicana: Boolean = false,
    var kupena: Boolean = false,
    var pocetStran: Int = 0,
    var pocetPrecitanych: Int = 0,
    var hodnotenie: Double = 0.0,
    var policka: String = "Všetko",
    val datumPridania: Calendar = Calendar.getInstance(),
    @PrimaryKey(autoGenerate = true)
    val id_knihy: Int = 0
) {
    var favorit: Boolean = false
    var vlastnosti: MutableList<Vlastnosti> = mutableListOf()
    var zanre: MutableList<Zanre> = mutableListOf()

    override fun toString(): String {
        return "$nazov, $autor, $rokVydania"
    }
}
