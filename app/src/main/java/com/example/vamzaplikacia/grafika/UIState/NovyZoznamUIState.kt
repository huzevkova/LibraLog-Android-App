package com.example.vamzaplikacia.grafika.UIState

import android.net.Uri

data class NovyZoznamUIState (
    val nazov: String = "",
    val obrazok: Uri? = null,
    val showDialog: Boolean = false
)