package com.example.vamzaplikacia.grafika.formular

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.grafika.zoznamiUI.VypisanieKnih
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.zoznamKnih
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre


private val modifierTextField = Modifier
    .padding(bottom = 16.dp)
    .fillMaxWidth()

@Composable
fun FormularKnihaScreen(viewModel: FormularKnihyViewModel = viewModel()){
    var hotovo by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    if (hotovo) {
        VypisanieKnih(zoznam = zoznamKnih)
        PridajButton(onClick = {hotovo = (!hotovo)})
    }
    else {
        Formular(viewModel, uiState, onClick = {
            hotovo = (!hotovo)
            PridajZadanuKnihu(uiState)
        })
    }

}

@Composable
fun Formular(viewModel: FormularKnihyViewModel, uiState: FormularKnihyUIState, onClick: () -> Unit) {

    val vybraneZanre = remember {mutableStateListOf<Boolean>()}
    for (i in Zanre.entries) {
        vybraneZanre.add(false)
    }
    val vybraneVlastnosti = remember {mutableStateListOf<Boolean>()}
    for (i in Vlastnosti.entries) {
        vybraneVlastnosti.add(false)
    }

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("NOVÁ KNIHA", modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineLarge)
        InputPole(
            modifierTextField,
            value = uiState.nazov,
            placeHolder = "Alchymista",
            onValueChange = { viewModel.setNazov(it) },
            labelText = "Názov knihy"
        )
        InputPole(
            modifierTextField,
            value = uiState.autor,
            placeHolder = "Paulo Coelho",
            onValueChange = { viewModel.setAutor(it) },
            labelText = "Autor knihy"
        )
        InputPole(
            modifierTextField,
            value = uiState.rok.toString(),
            onValueChange = {
                val rok = it.toIntOrNull() ?: 0
                viewModel.setRok(rok) },
            labelText = "Rok vydania",
            cisla = true
        )
        InputPole(
            modifierTextField,
            value = uiState.vydavatelstvo,
            onValueChange = { viewModel.setVydavatelstvo(it) },
            labelText = "Vydavateľstvo",
        )
        InputPole(
            modifierTextField,
            value = uiState.popis,
            placeHolder = "Sem napíšte stručný popis / obsah knihy.",
            onValueChange = { viewModel.setPopis(it) },
            labelText = "Popis knihy"
        )
        InputPole(
            modifierTextField,
            value = uiState.poznamky,
            placeHolder = "Sem napíšte svoje poznámky ku knihe.",
            onValueChange = { viewModel.setPoznamky(it)},
            labelText = "Poznámky ku knihe:"
        )
        CheckboxWithText(text = "Prečítaná", onValueChange = {viewModel.setPrecitana()})
        CheckboxWithText(text = "Na neskôr", onValueChange = {viewModel.setPozicana()})
        CheckboxWithText(text = "Požičaná", onValueChange = {viewModel.setPozicana()})
        CheckboxWithText(text = "Kúpená", onValueChange = {viewModel.setKupena()})
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)) {
            InputPole(
                modifier = Modifier.weight(1f),
                value = uiState.pocetStran.toString(),
                onValueChange = {
                    val pocet = it.toIntOrNull() ?: 0
                    viewModel.setPocetStran(pocet) },
                labelText = "Počet strán",
                cisla = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            InputPole(
                modifier = Modifier.weight(1f),
                value = uiState.pocetPrecitanych.toString(),
                onValueChange = {
                    val pocet = it.toIntOrNull() ?: 0
                    viewModel.setPocetPrecitanych(pocet)
                },
                labelText = "Z toho prečítaných",
                cisla = true
            )
        }
        Text("Žánre knihy:", Modifier
            .padding(4.dp)
            .align(Alignment.Start),
            style = MaterialTheme.typography.labelLarge)
        VyberMoznosti(Zanre.entries.map { it.zaner }, vybraneZanre, onClick = {vybraneZanre[it] = !vybraneZanre[it]})
        Text("Vlastnosti knihy:", Modifier
            .padding(4.dp)
            .align(Alignment.Start),
            style = MaterialTheme.typography.labelLarge)
        VyberMoznosti(Vlastnosti.entries.map {it.pridMeno}, vybraneVlastnosti, onClick = {vybraneVlastnosti[it] = !vybraneVlastnosti[it]})
        Button(modifier = Modifier.padding(top = 10.dp), onClick = onClick){
            Text(text = "OK")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VyberMoznosti(enumZoznam: List<String>, vybrane: List<Boolean>, onClick: (Int) -> Unit) {
    FlowRow(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.SpaceAround) {
        enumZoznam.forEachIndexed { index, zanre ->
            Button(
                onClick = {
                    onClick(index)
                },
                modifier = Modifier.padding(1.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (vybrane[index]) {
                        MaterialTheme.colorScheme.inversePrimary
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }
                )
            ) {
                Text(text = enumZoznam[index])
            }
        }
    }
}

@Composable
fun PridajButton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .wrapContentHeight(Alignment.Bottom),
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "Tlačidlo na pridanie niečoho.")
        }
    }
}

fun PridajZadanuKnihu(uiState: FormularKnihyUIState) {
    val zanre = mutableListOf<Zanre>()
    uiState.zanreVyber.forEachIndexed { index, b ->
        if (b) {
            zanre.add(Zanre.entries[index])
        }
    }
    val vlastnosti = mutableListOf<Vlastnosti>()
    uiState.vlastnostiVyber.forEachIndexed { index, b ->
        if (b) {
            vlastnosti.add(Vlastnosti.entries[index])
        }
    }

    val kniha = Kniha(uiState.nazov, uiState.autor, uiState.rok, uiState.vydavatelstvo,
        R.drawable.book, uiState.popis, uiState.poznamky, uiState.precitana, uiState.naNeskor,
        uiState.pozicana, uiState.kupena, uiState.pocetStran, uiState.pocetPrecitanych, uiState.hodnotenie)
    kniha.zanre = zanre
    kniha.vlastnosti = vlastnosti
    zoznamKnih.pridajKnihu(kniha)
}

@Composable
fun InputPole(modifier: Modifier, value: String = "", placeHolder: String = "", onValueChange: (String)-> Unit, labelText: String, cisla: Boolean = false) {
    TextField(
        modifier = modifier,
        label = { Text(labelText) },
        value = value,
        placeholder = {Text(text = placeHolder, color = Color.Gray)},
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = (if (cisla) KeyboardType.Number else KeyboardType.Text)
        )
    )
}

@Composable
fun CheckboxWithText(text: String, onValueChange: (Boolean) -> Unit) {
    var checkedState by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = checkedState,
                onValueChange = onValueChange,
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { checkedState = it }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}