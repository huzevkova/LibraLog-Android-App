package com.example.vamzaplikacia.grafika.zoznamiUI

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamzaplikacia.grafika.formular.NovyZoznamDialog
import com.example.vamzaplikacia.grafika.formular.NovyZoznamViewModel
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

val kniznica = Kniznica()

@Composable
fun KnizicaKartyScreen (showNovyZoznamDialog: Boolean = false, onClick: (ZoznamKnih) -> Unit) {
    var pocetCol = 2
    var ratio = 0.8f

    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            pocetCol = 3
            ratio = 1.2f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(pocetCol),
            modifier = Modifier.fillMaxSize()
        ) {
            items(kniznica.getZoznam().size) { index ->
                val zoznam = kniznica.getZoznam()[index]
                BookListCard(zoznam = zoznam, onClick, ratio = ratio)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListCard(zoznam: ZoznamKnih, onClick: (ZoznamKnih) -> Unit, ratio: Float) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(ratio),
        onClick = {onClick(zoznam)}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(zoznam.obrazok),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = zoznam.getNazov(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${zoznam.getSize()} položiek",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun KartyPreview() {
    VytvorZoznam()
    KnizicaKartyScreen(onClick = {})
}