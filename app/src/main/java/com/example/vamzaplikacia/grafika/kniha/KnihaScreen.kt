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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.knihy.Kniha


@Composable
fun KnihaScreen(kniha: Kniha, viewModel: KnihaViewModel = viewModel()){
    var sliderHodnoteniePosition by remember { mutableFloatStateOf(kniha.hodnotenie.toFloat() / 10) }
    var sliderStranyPosition by remember {
        mutableFloatStateOf(kniha.pocetPrecitanych.toFloat() / if (kniha.pocetStran == 0) 1 else kniha.pocetStran)
    }
    val pocetPrecitanychStran = (sliderStranyPosition * kniha.pocetStran).toInt()
    val hodnotenieKnihy = sliderHodnoteniePosition.toDouble() * 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val paint = if (kniha.obrazok == null) painterResource(R.drawable.book) else rememberAsyncImagePainter(
                kniha.obrazok
            )
            Image(
                painter = paint,
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
                TextField(text = "${stringResource(id = R.string.nazov_knihy_form)}:", true)
                TextField(text = kniha.nazov)

                Spacer(modifier = Modifier.height(8.dp))
                TextField(text = "${stringResource(id = R.string.autor)}:", true)
                TextField(text = kniha.autor)

                Spacer(modifier = Modifier.height(8.dp))
                TextField(text = "${stringResource(id = R.string.rok_vydania_form)}:", true)
                TextField(text = kniha.rokVydania.toString())

                Spacer(modifier = Modifier.height(8.dp))
                TextField(text = stringResource(id = R.string.zanre_knihy_form), true)
                TextField(text = kniha.zanre.joinToString(", "))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(text = "Prečítané:    $pocetPrecitanychStran / ${kniha.pocetStran}", true)
        Slider(value = sliderStranyPosition, steps = kniha.pocetStran, onValueChange = {
            sliderStranyPosition = it
        })

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = "Hodnotenie:  " + "%.1f".format(hodnotenieKnihy),true)
        Slider(value = sliderHodnoteniePosition, steps = 100, onValueChange = {
            sliderHodnoteniePosition = it
        })

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = stringResource(id = R.string.vlastnosti_knihy_form), true)
        TextField(text = kniha.vlastnosti.joinToString(", "))

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = "Popis:", true)
        TextField(text = kniha.popis)

        Spacer(modifier = Modifier.height(8.dp))
        TextField(text = "Poznámky:", true)
        TextField(text = kniha.poznamky)
    }

    viewModel.setHodnotenie(hodnotenieKnihy)
    viewModel.setPocetPrecitanych(pocetPrecitanychStran)
}

@Composable
fun TextField(text: String, bold: Boolean = false) {
    val textStyle = if (bold) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodySmall
    Text(
        text = text,
        style = textStyle
    )
}