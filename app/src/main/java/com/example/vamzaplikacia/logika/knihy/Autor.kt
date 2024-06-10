package com.example.vamzaplikacia.logika.knihy

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.vamzaplikacia.R

/**
 * Trieda pre autora, ukladajúca si všetky informácie o autorovi, zároveň aj tabuľka
 *
 * @param meno
 * @param datumNarodenia
 * @param datumUmrtia
 * @param obrazokCesta
 * @param popis
 * @param obrazok má defaultnú hodnotu
 * @param id_autora
 */
@Entity(tableName = "autori")
class Autor(
    val meno: String,
    var datumNarodenia: String = "X",
    var datumUmrtia: String = "X",
    var obrazokCesta: Uri? = null,
    var popis: String = "",
    var obrazok: Int = R.drawable.person,
    @PrimaryKey(autoGenerate = true)
    val id_autora: Int = 0)
{
    @Ignore
    var knihyKniznica: MutableList<Kniha> = mutableListOf()
    @Ignore
    var pocetKnih = 0
    @Ignore
    var pocetPrecitanych = 0

    fun nastavKnihy(zoznamKnih: ZoznamKnih) {
        knihyKniznica = zoznamKnih.vratPodlaPodmienky { it.autor == meno }
        pocetKnih = knihyKniznica.size
        pocetPrecitanych = knihyKniznica.sortedBy { it.precitana }.size
    }
}

/**
 * Pomocne Data triedy pre prácu s OpenLibraryAPI a vyhľadávanie informácii o autoroch z internetu.
 */
data class AutorData(
    val name: String,
    val birth_date: String?,
    val death_date: String?
)

data class VyhladaniAutori(
    val docs: List<VysledokHladaniaAutor>
)

data class VysledokHladaniaAutor(
    val key: String,
    val name: String
)

/**
 * Trieda obaľujúca zoznam autorov
 */
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

    fun odoberAutora(autor: Autor) {
        zoznam.remove(autor)
    }
}