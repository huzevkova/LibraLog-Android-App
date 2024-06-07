package com.example.vamzaplikacia.logika.knihy

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import java.util.Calendar

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

@Entity(tableName = "policky")
data class PolickaKniznice(
    val nazov: String,
    @PrimaryKey(autoGenerate = true)
    val id_policky: Int = 0
)

class ZoznamKnih(nazovZoznamu: String = "") {
    private val knihy: MutableList<Kniha> = mutableListOf()
    private var nazovZoznamu: String = nazovZoznamu
    var obrazokCesta: Uri? = null
    val obrazok: Int = R.drawable.library
    constructor(nazovZoznamu: String, obrazok: Uri?) : this(nazovZoznamu) {
        if (obrazok != null) {
            obrazokCesta = obrazok
        }
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

    fun odoberKnihu(kniha: Kniha?) {
        if (knihy.contains(kniha)) {
            knihy.remove(kniha)
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
            knihy.forEachIndexed { index, kniha -> println("${index+1}. $kniha") }
        }
    }

    /**
     * Vypise zoznam zoradení podľa posledného zoradenia.
     */
    fun vypisZoradene() {
        println("Zoradene knihy:")
        knihy.forEachIndexed { index, kniha -> println("${index+1}. $kniha") }
    }

    fun vratPodlaPodmienky(podmienka: (Kniha) -> Boolean): MutableList<Kniha> {
        val skupina = knihy.groupBy(podmienka)
        return (skupina[true] ?: mutableListOf()) as MutableList<Kniha>
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

class Kniznica {
    private val zoznamVsetkychKnih: ZoznamKnih = ZoznamKnih("Všetko")
    private val zoznamy: MutableList<ZoznamKnih> = mutableListOf()
    private val autoriKniznice: ZoznamAutorov = ZoznamAutorov()

    constructor() {
        zoznamy.add(zoznamVsetkychKnih)
    }
    fun pridajAutora(autor: Autor) {
        autoriKniznice.pridajAutora(autor)
    }

    fun pridajZoznam(zoznam: ZoznamKnih) {
        zoznamy.add(zoznam)
    }

    fun getVsetkyZoznamy(): MutableList<ZoznamKnih> {
        return zoznamy
    }

    fun getZoznamVsetkych(): ZoznamKnih {
        return zoznamVsetkychKnih
    }

    fun getZoznamAutorov(): ZoznamAutorov {
        return autoriKniznice
    }

    inner class KniznicaIterator : Iterator<ZoznamKnih> {
        private var index = 0

        override fun hasNext(): Boolean {
            return index < zoznamy.size
        }

        override fun next(): ZoznamKnih {
            return zoznamy[index++]
        }
    }

    fun iterator(): KniznicaIterator {
        return KniznicaIterator()
    }

    fun odoberAutora(autor: Autor) {
        autoriKniznice.odoberAutora(autor)
    }
}