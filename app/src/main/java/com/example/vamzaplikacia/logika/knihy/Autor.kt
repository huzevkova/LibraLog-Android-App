package com.example.vamzaplikacia.logika.knihy

import android.net.Uri
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.zoznamKnih
class Autor(val meno: String, val datumNarodenia: String = "X", val datumUmrtia: String = "X", var obrazokCesta: Uri? = null) {
    var popis: String = ""
    var knihyKniznica = zoznamKnih.vratPodlaPodmienky { it.autor == meno }
    var pocetKnih = knihyKniznica.size
    var pocetPrecitanych = knihyKniznica.sortedBy { it.precitana }.size
    var obrazok: Int = R.drawable.person
}

class ZoznamAutorov {
    private val zoznam: MutableList<Autor> = mutableListOf()

    fun pridajAutora(autor: Autor) {
        if (!zoznam.contains(autor)) {
            zoznam.add(autor)
        }
    }

    fun getZoznam(): MutableList<Autor> {
        return zoznam
    }
}