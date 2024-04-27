package com.example.vamzaplikacia.grafika.kniha

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.zoznamKnih


@Composable
fun KnihaScreen(index: Int, viewModel: KnihaViewModel = viewModel()){
    val book: Kniha = zoznamKnih.get(index)!!
    var sliderHodnoteniePosition by remember { mutableStateOf(book.getHodnotenie().toFloat() / 10) }
    var sliderStranyPosition by remember {
        mutableStateOf(book.getPocetPrecitanych().toFloat() / if (book.pocetStran == 0) 1 else book.pocetStran)
    }
    val pocetPrecitanychStran = (sliderStranyPosition * book.pocetStran).toInt()
    val hodnotenieKnihy = sliderHodnoteniePosition.toDouble() * 10
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(book.obrazok),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                TextField(text = "Názov:", true)
                TextField(text = book.nazov)

                Spacer(modifier = Modifier.height(8.dp))
                TextField(text = "Autor:", true)
                TextField(text = book.autor)

                Spacer(modifier = Modifier.height(8.dp))
                TextField(text = "Rok vydania:", true)
                TextField(text = book.rokVydania.toString())

                Spacer(modifier = Modifier.height(8.dp))
                TextField(text = "Žánre:", true)
                TextField(text = book.zanre.joinToString(", "))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(text = "Prečítané:    $pocetPrecitanychStran / ${book.pocetStran}", true)
        Slider(value = sliderStranyPosition, steps = book.pocetStran, onValueChange = {
            sliderStranyPosition = it
        })

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = "Hodnotenie:  " + "%.1f".format(hodnotenieKnihy),true)
        Slider(value = sliderHodnoteniePosition, steps = 100, onValueChange = {
            sliderHodnoteniePosition = it
        })

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = "Popis:", true)
        TextField(text = book.popis)

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = "Poznámky:", true)
        TextField(text = book.poznamky)
    }

    viewModel.setHodnotenie(hodnotenieKnihy)
    viewModel.setPocetPrecitanych(pocetPrecitanychStran)
    //book.hodnotenie = hodnotenieKnihy
    //book.pocetPrecitanych = pocetPrecitanychStran
}

@Composable
fun TextField(text: String, bold: Boolean = false) {
    val textStyle = if (bold) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodySmall
    Text(
        text = text,
        style = textStyle
    )
}

@Preview
@Composable
fun KnihaPreview() {
    VytvorZoznam()
    zoznamKnih.get(3)?.let { KnihaScreen(3) }
}