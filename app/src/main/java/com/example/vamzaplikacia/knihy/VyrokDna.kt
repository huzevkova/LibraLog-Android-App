package com.example.vamzaplikacia.knihy

import android.content.res.Resources
import com.example.vamzaplikacia.R

class VyrokDna (private val text: String) {
    val vyrok: String = text.split("-")[0]
    var zdroj: String = text.split("-")[1]

    override fun toString(): String {
        return vyrok + "\n" + zdroj
    }
}

class Vstup {

    var riadky: List<String> = mutableListOf()

    init {
        riadky = NacitajObsahRiadkov()
    }

    fun readTextFile(resources: Resources, resourceId: Int): String {
        val inputStream = resources.openRawResource(resourceId)
        val bufferedReader = inputStream.bufferedReader()
        val text = bufferedReader.use { it.readText() }
        return text
    }

    fun NacitajObsahRiadkov(): List<String> {
        val text: String = readTextFile(Resources.getSystem(), R.raw.vyroky)
        return text.split('\n')
    }

    fun NacitajVyroky(): MutableList<VyrokDna> {
        val vyroky: MutableList<VyrokDna> = mutableListOf()
        riadky.forEach({ vyroky.add(VyrokDna(it)) })
        return vyroky
    }
}