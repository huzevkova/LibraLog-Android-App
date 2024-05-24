package com.example.vamzaplikacia.logika

import android.net.Uri

data class NovyZoznamUIState (
    val nazov: String = "",
    val obrazok: Uri? = null,
    val showDialog: Boolean = false
)