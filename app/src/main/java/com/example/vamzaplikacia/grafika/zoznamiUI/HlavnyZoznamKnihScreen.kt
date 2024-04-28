package com.example.vamzaplikacia.grafika.zoznamiUI

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

@Composable
fun ZoznamKnihScreen(zoznam: ZoznamKnih, onClick: (Kniha) -> Unit) {
    VypisanieKnih(zoznam = zoznam, modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 0.dp),
        onClick)
}
