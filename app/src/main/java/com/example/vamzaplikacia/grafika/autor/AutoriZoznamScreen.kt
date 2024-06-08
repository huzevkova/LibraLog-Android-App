package com.example.vamzaplikacia.grafika.autor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.ZoznamAutorov

/**
 * Obrazovka zobrazujúca zoznam autorov - meno, obrázok, ukážka popisu, počet kníh.
 *
 * @param autori zoznam autorov
 * @param onClick funkcia s autorom čo sa vykoná po kliknutí
 */
@Composable
fun AutoriZoznamScreen(autori: ZoznamAutorov, onClick: (Autor) -> Unit) {
    Column (modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()) {
        for (autor in autori.getZoznam()) {
            val knihaSklonovanie = if (autor.pocetKnih == 0 || autor.pocetKnih!! > 4) {
                "kníh"
            } else if (autor.pocetKnih == 1) {
                "kniha"
            } else {
                "knihy"
            }

            val paint = if (autor.obrazokCesta == null) painterResource(autor.obrazok) else rememberAsyncImagePainter(
                autor.obrazokCesta
            )

            ListItem(
                modifier = Modifier.clickable { onClick(autor) },
                headlineContent = { Text(autor.meno) },
                supportingContent = {
                    Text(autor.popis, maxLines = 2, overflow = TextOverflow.Ellipsis)
                },
                trailingContent = {
                    Text(text = "${autor.pocetKnih} " + knihaSklonovanie)
                },
                leadingContent = {
                    Image(
                        painter = paint,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
            )
        }
    }
}
