package com.example.vamzaplikacia.grafika.autor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
import java.util.Calendar

val autori = ZoznamAutorov()

fun VytvorZoznamAutorov() {
    val calendar = Calendar.getInstance()
    calendar[Calendar.YEAR] = 1892
    calendar[Calendar.MONTH] = Calendar.JANUARY // January is 0
    calendar[Calendar.DAY_OF_MONTH] = 3
    val au = Autor("J. R. R. Tolkien", calendar, calendar)
    au.popis = "John Ronald Reuel Tolkien CBE FRSL bol anglický spisovateľ, autor knihy The Hobbit a jej pokračovania The Lord of the Rings, jeho najznámejšieho diela. Bol významným jazykovedcom, znalcom anglosaštiny a starej nórčiny."
    au.obrazok = R.drawable.tolkien
    autori.pridajAutora(au)
}

@Composable
fun AutoriZoznamScreen(onClick: (Autor) -> Unit) {
    Row (horizontalArrangement = Arrangement.SpaceBetween) {
        Column (modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()) {
            for (autor in autori.getZoznam()) {
                var knihaSklonovanie = ""
                if (autor.pocetKnih == 0 || autor.pocetKnih > 4) {
                    knihaSklonovanie = "kníh"
                } else if (autor.pocetKnih == 1) {
                    knihaSklonovanie = "kniha"
                } else {
                    knihaSklonovanie = "knihy"
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
}

@Preview
@Composable
fun AutoriPreview() {
    VytvorZoznam()
    VytvorZoznamAutorov()
    AutoriZoznamScreen(onClick = {})
}