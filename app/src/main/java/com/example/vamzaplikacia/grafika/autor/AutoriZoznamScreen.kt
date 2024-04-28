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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.ZoznamAutorov

val autori = ZoznamAutorov()

fun vytvorZoznamAutorov() {
    val au = Autor("J. R. R. Tolkien", "03.01.1892", "01.01.1973")
    au.popis = "John Ronald Reuel Tolkien CBE FRSL bol anglický spisovateľ, autor knihy The Hobbit a jej pokračovania The Lord of the Rings, jeho najznámejšieho diela. Bol významným jazykovedcom, znalcom anglosaštiny a starej nórčiny."
    au.obrazok = R.drawable.tolkien
    autori.pridajAutora(au)
}

@Composable
fun AutoriZoznamScreen(onClick: (Autor) -> Unit) {
    Column (modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()) {
        for (autor in autori.getZoznam()) {
            val knihaSklonovanie = if (autor.pocetKnih == 0 || autor.pocetKnih > 4) {
                "kníh"
            } else if (autor.pocetKnih == 1) {
                "kniha"
            } else {
                "knihy"
            }

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
                        painter = painterResource(autor.obrazok),
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

@Preview
@Composable
fun AutoriPreview() {
    VytvorZoznam()
    vytvorZoznamAutorov()
    AutoriZoznamScreen(onClick = {})
}