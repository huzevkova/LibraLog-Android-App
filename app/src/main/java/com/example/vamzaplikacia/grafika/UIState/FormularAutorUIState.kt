package com.example.vamzaplikacia.grafika.UIState

import android.net.Uri

data class FormularAutorUIState (
    val menoAutora: String = "",
    val popis: String = "",
    val datumNar: String = "",
    val datumUmrtia: String = "",
    val obrazok: Uri? = null
)