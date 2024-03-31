package com.example.vamzaplikacia.grafika.zoznamiUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.zoznamKnih
import java.util.Date

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
    zoznamKnih.get(1)?.favorit = true;
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
        obrazok = R.drawable.hobit
    )
    )
}

@Composable
fun VypisanieKnih(zoznam: ZoznamKnih) {
    Row (horizontalArrangement = Arrangement.SpaceBetween) {
        Column (modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = 40.dp)) {
            for (kniha in zoznam.iterator()) {
                ListItem(
                    headlineContent = { Text(kniha.nazov) },
                    supportingContent = { Text(kniha.autor + ", " + kniha.rokVydania) },
                    trailingContent = {
                        if (kniha.favorit) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                            )
                        }
                        else {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "Localized description",
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
}
