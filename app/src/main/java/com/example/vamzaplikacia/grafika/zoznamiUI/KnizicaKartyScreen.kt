package com.example.vamzaplikacia.grafika.zoznamiUI

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

val kniznica = Kniznica()

@Composable
fun KnizicaKartyScreen (onClick: (ZoznamKnih) -> Unit, onDeleteClick: () -> Unit) {
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
                BookListCard(zoznam = zoznam, onClick, onDeleteClick = onDeleteClick, ratio = ratio)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookListCard(zoznam: ZoznamKnih, onClick: (ZoznamKnih) -> Unit, onDeleteClick: () -> Unit, ratio: Float) {

    var remove by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(ratio)
            .combinedClickable(
                onClick = {
                    if (!remove) {
                        onClick(zoznam)
                    }
                },
                onLongClick = {
                    remove = true
                }
            )
    ) {
        if (remove) {
            Row(modifier = Modifier
                .fillMaxSize()) {
                IconButton(onClick = {
                    onDeleteClick()
                    remove = false
                                     }, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete", modifier = Modifier.fillMaxSize())
                }
                IconButton(onClick = {remove = false}, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)) {
                    Icon(Icons.Filled.Done, contentDescription = "Done", modifier = Modifier.fillMaxSize())
                }
            }
        } else {
            val paint = if (zoznam.obrazokCesta == null) painterResource(zoznam.obrazok) else rememberAsyncImagePainter(
                zoznam.obrazokCesta
            )
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
                        painter = paint,
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
}