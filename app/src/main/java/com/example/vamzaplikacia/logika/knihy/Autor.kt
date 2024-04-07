package com.example.vamzaplikacia.logika.knihy

import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.zoznamKnih
import java.util.Calendar

class Autor(val meno: String, val datumNarodenia: Calendar, val datumUmrtia: Calendar, var obrazok: Int = R.drawable.person) {
    var popis: String = "";
    var knihyKniznica = zoznamKnih.vratPodlaPodmienky { it.autor == meno };
    var pocetKnih = knihyKniznica.size
    var pocetPrecitanych = knihyKniznica.sortedBy { it.precitana }
}