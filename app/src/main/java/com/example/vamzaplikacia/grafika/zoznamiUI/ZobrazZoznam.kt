package com.example.vamzaplikacia.grafika.zoznamiUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.enumy.Zanre
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.zoznamKnih

fun VytvorZoznam() {
    zoznamKnih.pridajKnihu(
        Kniha(
        "Veľký Gatsby",
        "F. Scott Fitzgerald",
        1925,
    )
    )
    zoznamKnih.pridajKnihu(
        Kniha(
        "Alchymista",
        "Paulo Coelho",
        1988,
    )
    )
    zoznamKnih.get(1)?.favorit = true
    zoznamKnih.pridajKnihu(
        Kniha(
        "1984",
        "George Orwell",
        1949
    )
    )
    zoznamKnih.pridajKnihu(
        Kniha(
        "Hobit",
        "J. R. R. Tolkien",
        1937,
            "Ikar",
        obrazok = R.drawable.hobit,
            popis = "Kniha o hobitovi Bilbovi a 13 trpaslíkoch.",
            poznamky = "Super kniha",
            pocetStran = 200,
            pocetPrecitanych = 150,
            hodnotenie = 9.9,
    )
    )
    var zanre: MutableList<Zanre> = mutableListOf()
    zanre.add(Zanre.FANTASY)
    zanre.add(Zanre.DOBRODRUZNE)
    zoznamKnih.get(3)?.zanre = zanre
}


@Composable
fun VypisanieKnih(zoznam: ZoznamKnih, modifier: Modifier, onClick: (Kniha) -> Unit) {
    Column (modifier = modifier) {
        for (kniha in zoznam.iterator()) {
            ListItem(
                modifier = Modifier.clickable { onClick(kniha) },
                headlineContent = { Text(kniha.nazov) },
                supportingContent = { Text(kniha.autor + ", " + kniha.rokVydania) },
                trailingContent = {
                    if (kniha.favorit) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Obľúbená kniha",
                        )
                    } else {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "Kniha",
                        )
                    }
                },
                leadingContent = {
                    Image(
                        painter = painterResource(kniha.obrazok),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            )
        }
    }
}
