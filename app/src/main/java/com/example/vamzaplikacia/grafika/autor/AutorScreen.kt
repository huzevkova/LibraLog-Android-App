package com.example.vamzaplikacia.grafika.autor

import android.icu.text.SimpleDateFormat
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.grafika.zoznamiUI.VypisanieKnih
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

@Composable
fun AutorScreen(autor: Autor, onClick: (Kniha) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth(1f)) {
        Top(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.primary)
        )

        Bottom(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 120.dp)
                .align(Alignment.Center),
            autor,
            onClick
        )

        Center(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 500.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            autor
        )
    }
}

@Composable
private fun Top(modifier: Modifier) {
    Column(modifier = modifier) {
    }
}

@Composable
private fun Bottom(modifier: Modifier, autor: Autor, onClick: (Kniha) -> Unit) {
    Column(
        modifier
            .background(Color.White)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.height(100.dp))

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        Text(text = autor.meno, style = MaterialTheme.typography.headlineMedium)
        Text(text = "${dateFormat.format(autor.datumNarodenia?.time ?: "X")} - ${dateFormat.format(
            autor.datumUmrtia?.time ?: "X"
        )}", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = autor.popis, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 20.dp, end = 20.dp))

        val style = MaterialTheme.typography.titleMedium
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Text(text = "Počet prečítaných kníh:",
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

        Row() {
            Text(text = "Počet diel v knižnici:",
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
        VypisanieKnih(zoznam = ZoznamKnih(zoznam = autor.knihyKniznica.toMutableList()), onClick = onClick)
    }
}

@Composable
fun Center(modifier: Modifier, autor: Autor) {
    Column(modifier = modifier.padding(top = 50.dp))
    {
        Image(
            painter = painterResource(autor.obrazok),
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

@Preview
@Composable
fun showAuthor() {
    VytvorZoznam()
    VytvorZoznamAutorov()
    AutorScreen(autor = autori.getZoznam()[0], onClick = {})
}