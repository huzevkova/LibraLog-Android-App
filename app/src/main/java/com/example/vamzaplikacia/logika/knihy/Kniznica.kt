package com.example.vamzaplikacia.logika.knihy

import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import java.util.Calendar

data class Kniha(
    val nazov: String,
    val autor: String,
    val rokVydania: Int,
    val vydavatelstvo: String = "Neznáme",
    val obrazok: Int = R.drawable.book,
    var popis: String = "Bez popisu.",
    var poznamky: String = "Bez poznámok.",
    var precitana: Boolean = false,
    var naNeskor: Boolean = false,
    var pozicana: Boolean = false,
    var kupena: Boolean = false,
    var pocetStran: Int = 0,
    private var pocetPrecitanych: Int = 0,
    private var hodnotenie: Double = 0.0
) {
    val datumPridania: Calendar = Calendar.getInstance()
    var favorit: Boolean = false

    var vlastnosti: MutableList<Vlastnosti> = mutableListOf()
    var zanre: MutableList<Zanre> = mutableListOf()

    override fun toString(): String {
        return "$nazov, $autor, $rokVydania"
    }

    fun setHodnotenie(nove: Double) {
        hodnotenie = nove
    }

    fun setPrecitaneStrany(nove: Int) {
        pocetPrecitanych = nove
    }

    fun getHodnotenie(): Double {
        return hodnotenie
    }

    fun getPocetPrecitanych(): Int {
        return pocetPrecitanych
    }
}

class ZoznamKnih(val obrazok: Int = R.drawable.library) {
    private val knihy: MutableList<Kniha> = mutableListOf()
    private var nazovZoznamu: String = ""

    constructor(nazovZoznamu: String): this() {
        this.nazovZoznamu = nazovZoznamu
    }

    constructor(zoznam: MutableList<Kniha>): this() {
        for (k in zoznam) {
            knihy.add(k)
        }
    }

    fun getNazov(): String {
        return nazovZoznamu
    }

    fun pridajKnihu(novaKniha: Kniha) {
        if (!knihy.contains(novaKniha)) {
            knihy.add(novaKniha)
        }
    }

    fun odoberKnihu(index: Int) {
        if (index in 0 until knihy.size) {
            knihy.removeAt(index)
        }
    }

    fun get(index: Int): Kniha? {
        if (index in 0 until knihy.size) {
            return knihy[index]
        }
        return null
    }

    fun get(kniha: Kniha): Int {
        return if (knihy.contains(kniha)) knihy.indexOf(kniha) else -1
    }

    fun getSize(): Int {
        return knihy.size
    }

    fun najdiKnihuPodla(text: String): Kniha? {
        return knihy.find {
            (it.nazov == text || it.autor == text || it.rokVydania.toString() == text)
        }
    }

    /**
     * Vypise zoznam v poradi v akom boli knihy pridavane - originalny zoznam.
     */
    fun vypisZoznam() {
        if (knihy.isEmpty()) {
            println("Zoznam je prázdny.")
        } else {
            println("Knihy v zozname${nazovZoznamu}:")
            this.zoradPodla { kniha1 -> kniha1.datumPridania.toString()}
            knihy.forEachIndexed { index, kniha -> println("${index+1}. ${kniha.toString()}") }
        }
    }

    /**
     * Vypise zoznam zoradení podľa posledného zoradenia.
     */
    fun vypisZoradene() {
        println("Zoradene knihy:")
        knihy.forEachIndexed { index, kniha -> println("${index+1}. ${kniha.toString()}") }
    }

    fun vratPodlaPodmienky(podmienka: (Kniha) -> Boolean): List<Kniha> {
        val skupina = knihy.groupBy(podmienka)
        return skupina[true] ?: mutableListOf<Kniha>()
    }

    fun zoradPodla(podmienka: (Kniha) -> String) {
        knihy.sortBy(podmienka)
    }
    inner class KnihyIterator : Iterator<Kniha> {
        private var index = 0

        override fun hasNext(): Boolean {
            return index < knihy.size
        }

        override fun next(): Kniha {
            return knihy[index++]
        }
    }

    fun iterator(): KnihyIterator {
        return KnihyIterator()
    }
}

class Kniznica() {
    private val zoznamy: MutableList<ZoznamKnih> = mutableListOf()

    fun pridajZoznam(zoznam: ZoznamKnih) {
        zoznamy.add(zoznam)
    }

    fun getZoznam(): MutableList<ZoznamKnih> {
        return zoznamy
    }
}