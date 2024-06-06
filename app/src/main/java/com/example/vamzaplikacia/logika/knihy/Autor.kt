package com.example.vamzaplikacia.logika.knihy

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.zoznamKnih

@Entity(tableName = "autori")
class Autor(
    val meno: String,
    val datumNarodenia: String = "X",
    val datumUmrtia: String = "X",
    var obrazokCesta: Uri? = null,
    var popis: String = "",
    var obrazok: Int = R.drawable.person,
    @PrimaryKey(autoGenerate = true)
    val id_autora: Int = 0)
{
    @Ignore
    var knihyKniznica = zoznamKnih.vratPodlaPodmienky { it.autor == meno }
    @Ignore
    var pocetKnih = knihyKniznica.size
    @Ignore
    var pocetPrecitanych = knihyKniznica.sortedBy { it.precitana }.size
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