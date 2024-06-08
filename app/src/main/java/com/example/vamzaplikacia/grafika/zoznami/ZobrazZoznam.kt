package com.example.vamzaplikacia.grafika.zoznami

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil.compose.rememberAsyncImagePainter
import com.example.vamzaplikacia.R
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


@Composable
fun VypisanieKnih(zoznam: ZoznamKnih, modifier: Modifier, onClick: (Kniha) -> Unit) {
    Column (modifier = modifier) {
        for (kniha in zoznam.iterator()) {
            val paint = if (kniha.obrazok == null) painterResource(R.drawable.book) else rememberAsyncImagePainter(
                kniha.obrazok
            )
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
                        painter = paint,
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
