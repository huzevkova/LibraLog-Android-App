package com.example.vamzaplikacia.grafika.UIState

import android.net.Uri

/**
 * UI state pre nový zoznam
 */
data class NovyZoznamUIState (
    val nazov: String = "",
    val obrazok: Uri? = null,
    val showDialog: Boolean = false
)