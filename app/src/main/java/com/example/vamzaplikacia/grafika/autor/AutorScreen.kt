package com.example.vamzaplikacia.grafika.autor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.vamzaplikacia.grafika.zoznami.VypisanieKnih
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.vamzaplikacia.R

/**
 * Obrazovka zobrazujúca detail autora - obrázok, meno, roky, popis, počet kníh a zoznam kníh.
 * @param autor autor ktorého sa to týka
 * @param onClick funkcia s knihou čo sa vykoná po kliknutí
 */
@Composable
fun AutorScreen(autor: Autor, onClick: (Kniha) -> Unit) {

    if (autor.datumNarodenia == "" && autor.datumUmrtia == "") {
        val viewModel: AutorViewModel = viewModel()
        val authorInfo by viewModel.authorInfo

        LaunchedEffect(autor.meno) {
            viewModel.fetchAuthorInfo(autor.meno)
        }

        when {
            authorInfo != null -> {
                autor.datumNarodenia = skonvertujDatum(authorInfo!!.birth_date!!)
                autor.datumUmrtia = skonvertujDatum(authorInfo!!.death_date!!)
            }
        }
    }

    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth(1f)
        .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .align(Alignment.TopCenter)
            .background(MaterialTheme.colorScheme.primary))
        {
        }

        Bottom(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)
                .padding(top = 100.dp),
            autor,
            onClick
        )

        Center(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 300.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            autor
        )
    }
}

/**
 * UI pre spodnú časť obrazovky - informácie
 *
 * @param modifier modifikátor
 * @param autor autor ktorého sa to týka
 * @param onClick funkcia s knihou čo sa vykoná po kliknutí
 */
@Composable
private fun Bottom(modifier: Modifier, autor: Autor, onClick: (Kniha) -> Unit) {
    Column(
        modifier
            .background(Color.White)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.height(80.dp))

        Text(text = autor.meno, style = MaterialTheme.typography.headlineMedium)
        Text(text = "${autor.datumNarodenia} - ${autor.datumUmrtia}", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = autor.popis, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 20.dp, end = 20.dp))

        val style = MaterialTheme.typography.titleMedium
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(text = stringResource(R.string.pocet_precitanych_knih),
                style = style,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(3f)
            )
            Text(text = "${autor.pocetPrecitanych}",
                style = style,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
            )
        }

        Row {
            Text(text = stringResource(R.string.pocet_diel_v_kniznici),
                style = style,
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 20.dp)
            )
            Text(text = "${autor.pocetKnih}",
                style = style,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        VypisanieKnih(zoznam = ZoznamKnih(zoznam = autor.knihyKniznica.toMutableList()),modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
            onClick = onClick,
            onLongClick = {})

    }
}

/**
 * UI pre strednú časť obrazovky - obrázok.
 *
 *  * @param modifier modifikátor
 *  * @param autor autor ktorého sa to týka
 */
@Composable
fun Center(modifier: Modifier, autor: Autor) {
    Column(modifier = modifier)
    {
        val paint = if (autor.obrazokCesta == null) painterResource(autor.obrazok) else rememberAsyncImagePainter(
            autor.obrazokCesta
        )
        Image(
            painter = paint,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .border(
                    BorderStroke(4.dp, Color.White),
                    CircleShape
                )
                .clip(CircleShape)
        )
    }
}

private fun skonvertujDatum(datum: String) : String {

    val mesiacAnj = datum.substring(datum.indexOf(" ")+1, datum.lastIndexOf(" "))

    val mesiacCislo = when (mesiacAnj) {
        "January" -> "1"
        "February" -> "2"
        "March" -> "3"
        "April" -> "4"
        "May" -> "5"
        "June" -> "6"
        "July" -> "7"
        "August" -> "8"
        "September" -> "9"
        "October" -> "10"
        "November" -> "11"
        "December" -> "12"
        else -> ""
    }

    var datumNovy = datum.replace(" ", ".")
    return datumNovy.replace(mesiacAnj, mesiacCislo)
}
