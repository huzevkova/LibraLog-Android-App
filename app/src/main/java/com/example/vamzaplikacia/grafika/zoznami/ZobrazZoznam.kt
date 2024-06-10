package com.example.vamzaplikacia.grafika.zoznami

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

/**
 * Obrazovka zobrazujúca zoznam kníh
 *
 * @param zoznam zoznam knih
 * @param onClick funkcia s knihou ktorá sa vykoná po kliknutí na knihu
 */
@Composable
fun ZoznamKnihScreen(zoznam: ZoznamKnih, onClick: (Kniha) -> Unit, onLongClick: (Kniha) -> Unit) {

    VypisanieKnih(zoznam = zoznam, modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 0.dp),
        onClick,
        onLongClick)
}

/**
 * Vypísanie kníh v zozname
 *
 * @param zoznam zoznam knih
 * @param modifier modifikátor
 * @param onClick funkcia s knihou ktorá sa vykoná po kliknutí na knihu
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VypisanieKnih(zoznam: ZoznamKnih, modifier: Modifier, onClick: (Kniha) -> Unit, onLongClick: (Kniha) -> Unit) {

    Column(modifier = modifier) {
        for (kniha in zoznam.iterator()) {
            var icon by remember { mutableStateOf(if (kniha.favorit) Icons.Filled.Favorite else Icons.Filled.Info) }
            val paint =
                if (kniha.obrazok == null) painterResource(R.drawable.book) else rememberAsyncImagePainter(
                    kniha.obrazok
                )
            ListItem(
                modifier = Modifier
                    .combinedClickable(
                        onClick = { onClick(kniha) },
                        onLongClick = {
                            icon = if (kniha.favorit) Icons.Filled.Info else Icons.Filled.Favorite
                            onLongClick(kniha)
                        }
                    ),
                headlineContent = { Text(kniha.nazov) },
                supportingContent = { Text(kniha.autor + ", " + kniha.rokVydania) },
                trailingContent = {
                    if (kniha.favorit) {
                        Icon(
                            icon,
                            contentDescription = stringResource(R.string.oblubena_kniha),
                        )
                    } else {
                        Icon(
                            icon,
                            contentDescription = stringResource(R.string.kniha),
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
