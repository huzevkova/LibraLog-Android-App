package com.example.vamzaplikacia.knihy

import java.util.Date

class Kniha(val nazov: String, val autor: String, val rokVydania: Int, val datumPridania: Date = Date()) {
    var favorit: Boolean = false
    var precitana: Boolean = false
    var naNeskor: Boolean = false
    var popis: String = "Bez popisu."

    override fun toString(): String {
        return "$nazov, $autor, $rokVydania"
    }
}

class ZoznamKnih() {
    private val knihy: MutableList<Kniha> = mutableListOf()
    private lateinit var nazovZoznamu: String

    constructor(nazovZoznamu: String): this() {
        this.nazovZoznamu = nazovZoznamu
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
            println("Knihy v zozname${if (!this::nazovZoznamu.isInitialized) "" else " $nazovZoznamu"}:")
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