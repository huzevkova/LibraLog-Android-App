package com.example.vamzaplikacia.logika.knihy

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.core.content.ContextCompat.getString
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vamzaplikacia.R

/**
 * Trieda predstavujúca poličku, zároveň tabuľka, využitá na ukladanie do databázy
 *
 * @param nazov
 * @param id_policky
 */
@Entity(tableName = "policky")
data class PolickaKniznice(
    val nazov: String,
    val obrazok: Uri? = null,
    @PrimaryKey(autoGenerate = true)
    val id_policky: Int = 0
)

/**
 * Trieda obaľujúca zoznam kníh a potrebné funkcie
 *
 * @param nazovZoznamu
 */
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

/**
 * Trieda pre celú knižnicu aplikácie so všetkými knihy a autormi
 *
 * @param context kontext v ktorom je vytvorená
 */
class Kniznica(context: Context) {
    private val zoznamVsetkychKnih: ZoznamKnih
    private val zoznamy: MutableList<ZoznamKnih> = mutableListOf()
    private val autoriKniznice: ZoznamAutorov = ZoznamAutorov()

    init {
        zoznamVsetkychKnih = ZoznamKnih(getString(context, R.string.vsetko))
        zoznamy.add(zoznamVsetkychKnih)
    }
    fun pridajAutora(autor: Autor) {
        if (autoriKniznice.getZoznam().find { _autor -> _autor.meno == autor.meno } == null) {
            autoriKniznice.pridajAutora(autor)
        }
    }

    fun pridajZoznam(zoznam: ZoznamKnih) {
        if (zoznamy.find { _zoznam -> _zoznam.getNazov() == zoznam.getNazov() } == null) {
            zoznamy.add(zoznam)
        }
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

    fun getZoznam(policka: String): ZoznamKnih? {
        return zoznamy.find { zoznamKnih ->  zoznamKnih.getNazov() == policka}
    }
}