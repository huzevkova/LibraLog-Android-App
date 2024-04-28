package com.example.vamzaplikacia.grafika.zoznamiUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.zoznamKnih

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HlavnyZoznamKnihScreen(onClick: (Kniha) -> Unit) {
    VypisanieKnih(zoznam = zoznamKnih, onClick)
}

@Composable
fun VedlajsiZoznamKnihScreen(zoznam: ZoznamKnih, onClick: (Kniha) -> Unit) {
    VypisanieKnih(zoznam = zoznam, onClick)
}
