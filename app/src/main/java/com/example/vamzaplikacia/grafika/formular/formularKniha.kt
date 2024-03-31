package com.example.vamzaplikacia.grafika.formular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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


private var nazov = ""
private var autor = ""
private var rok = 0
private var vydavatelstvo = ""
private var popis = ""
private var poznamky = ""
private var precitana = false
private var naNeskor = false
private var pozicana = false
private var kupena = false
private var pocetStran = 0
private var pocetPrecitanych = -1
private var hodnotenie = -1

private val modifierTextField = Modifier
    .padding(bottom = 16.dp)
    .fillMaxWidth()

@Composable
fun FormularKniha(){
    var hotovo by remember { mutableStateOf(false) }

    if (hotovo) {
        VypisanieKnih(zoznam = zoznamKnih)
        PridajButton(onClick = {hotovo = (!hotovo)})
    }
    else {
        Formular(onClick = {
        PridajZadanuKnihu(nazov, autor, rok)
        hotovo = (!hotovo)})
    }

}

@Composable
fun Formular(onClick: () -> Unit) {
    var nazovInput by remember { mutableStateOf("") }
    var autorInput by remember { mutableStateOf("") }
    var rokInput by remember { mutableStateOf("") }
    var vydavatelInput by remember { mutableStateOf("") }
    var popisInput by remember { mutableStateOf("") }
    var poznamkyInput by remember { mutableStateOf("") }
    var precitanaInput by remember { mutableStateOf(false) }
    var naNeskorInput by remember { mutableStateOf(false) }
    var pozicanaInput by remember { mutableStateOf(false) }
    var kupenaInput by remember { mutableStateOf(false) }
    var pocetStranInput by remember { mutableStateOf("") }
    var pocetPrecitanychInput by remember { mutableStateOf("") }
    var hodnotenieInput by remember { mutableStateOf("") }

    nazov = nazovInput
    autor = autorInput
    rok = rokInput.toIntOrNull() ?: 0
    vydavatelstvo = vydavatelInput
    popis = popisInput
    poznamky = poznamkyInput
    precitana = precitanaInput
    naNeskor = naNeskorInput
    pozicana = pozicanaInput
    kupena = kupenaInput
    pocetStran = pocetStranInput.toIntOrNull() ?: 0
    pocetPrecitanych = pocetPrecitanychInput.toIntOrNull() ?: 0
    hodnotenie = hodnotenieInput.toIntOrNull() ?: 0

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("NOVÁ KNIHA", modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally), style = MaterialTheme.typography.headlineLarge)
        InputPole(
            modifierTextField,
            value = nazovInput,
            placeHolder = "Alchymista",
            onValueChange = { nazovInput = it },
            labelText = "Názov knihy"
        )
        InputPole(
            modifierTextField,
            value = autorInput,
            placeHolder = "Paolo Coelho",
            onValueChange = { autorInput = it },
            labelText = "Autor knihy"
        )
        InputPole(
            modifierTextField,
            value = rokInput,
            onValueChange = { rokInput = it },
            labelText = "Rok vydania",
            cisla = true
        )
        InputPole(
            modifierTextField,
            value = vydavatelInput,
            onValueChange = { vydavatelInput = it },
            labelText = "Vydavateľstvo",
            cisla = true
        )
        InputPole(
            modifierTextField,
            value = popisInput,
            onValueChange = { popisInput = it },
            labelText = "Popis knihy"
        )
        InputPole(
            modifierTextField,
            value = poznamkyInput,
            onValueChange = { poznamkyInput = it },
            labelText = "Poznámky ku knihe:"
        )
        CheckboxWithText(text = "Prečítaná", onValueChange = {precitanaInput = (!precitanaInput)})
        CheckboxWithText(text = "Na neskôr", onValueChange = {naNeskorInput = (!naNeskorInput)})
        CheckboxWithText(text = "Požičaná", onValueChange = {pozicanaInput = (!pozicanaInput)})
        CheckboxWithText(text = "Kúpená", onValueChange = {kupenaInput = (!kupenaInput)})
        Row(modifier = Modifier.fillMaxWidth()) {
            InputPole(
                modifier = Modifier.weight(1f),
                value = pocetStranInput,
                onValueChange = { pocetStranInput = it },
                labelText = "Počet strán",
                cisla = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            InputPole(
                modifier = Modifier.weight(1f),
                value = pocetPrecitanychInput,
                onValueChange = { pocetPrecitanychInput = it },
                labelText = "Z toho prečítaných",
                cisla = true
            )
        }
        Button(onClick = onClick){
            Text(text = "OK")
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

fun PridajZadanuKnihu(nazov: String, autor: String, rok: Int) {
    zoznamKnih.pridajKnihu(Kniha(nazov, autor, rok))
}

@Composable
fun InputPole(modifier: Modifier, value: String, placeHolder: String = "", onValueChange: (String)-> Unit, labelText: String, cisla: Boolean = false) {
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