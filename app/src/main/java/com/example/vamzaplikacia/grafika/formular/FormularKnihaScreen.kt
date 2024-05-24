package com.example.vamzaplikacia.grafika.formular

import FilePickerButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre


private val modifierTextField = Modifier
    .padding(bottom = 16.dp)
    .fillMaxWidth()

@Composable
fun FormularKnihaScreen(viewModel: FormularKnihyViewModel = viewModel(), uiState: FormularKnihyUIState, onClick: () -> Unit){
    Formular(viewModel, uiState, onClick = onClick)
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

    var sliderHodnoteniePosition by remember { mutableFloatStateOf(0f) }
    val hodnotenieKnihy = sliderHodnoteniePosition.toDouble() * 10

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        InputPole(
            modifierTextField,
            value = uiState.nazov,
            placeHolder = stringResource(R.string.nazov_knihy_placeholder),
            onValueChange = { viewModel.setNazov(it) },
            labelText = stringResource(R.string.nazov_knihy_form)
        )
        InputPole(
            modifierTextField,
            value = uiState.autor,
            placeHolder = stringResource(R.string.autor_placeholder),
            onValueChange = { viewModel.setAutor(it) },
            labelText = stringResource(R.string.autor_knihy_form)
        )
        InputPole(
            modifierTextField,
            value = uiState.rok,
            onValueChange = {
                viewModel.setRok(it) },
            labelText = stringResource(R.string.rok_vydania_form),
            cisla = true
        )
        InputPole(
            modifierTextField,
            value = uiState.vydavatelstvo,
            onValueChange = { viewModel.setVydavatelstvo(it) },
            labelText = stringResource(R.string.vydavatelstvo_form),
        )
        Spacer(modifier = Modifier.height(8.dp))
        FilePickerButton{viewModel.setObrazok(it)}
        Spacer(modifier = Modifier.height(8.dp))
        com.example.vamzaplikacia.grafika.kniha.TextField(
            text = "Hodnotenie:  " + "%.1f".format(
                hodnotenieKnihy
            ), true
        )
        Slider(value = sliderHodnoteniePosition, steps = 100, onValueChange = {
            sliderHodnoteniePosition = it
        })
        Spacer(modifier = Modifier.height(8.dp))
        InputPole(
            modifierTextField,
            value = uiState.popis,
            placeHolder = stringResource(R.string.popis_placeholder),
            onValueChange = { viewModel.setPopis(it) },
            labelText = stringResource(R.string.popis_knihy_form)
        )
        InputPole(
            modifierTextField,
            value = uiState.poznamky,
            placeHolder = stringResource(R.string.poznamky_kniha_placeholder),
            onValueChange = { viewModel.setPoznamky(it)},
            labelText = stringResource(R.string.poznamky_kniha_form)
        )
        CheckboxWithText(text = stringResource(R.string.precitana), onValueChange = {viewModel.setPrecitana()})
        CheckboxWithText(text = stringResource(R.string.na_neskor), onValueChange = {viewModel.setNaNeskor()})
        CheckboxWithText(text = stringResource(R.string.pozicana), onValueChange = {viewModel.setPozicana()})
        CheckboxWithText(text = stringResource(R.string.kupena), onValueChange = {viewModel.setKupena()})
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)) {
            InputPole(
                modifier = Modifier.weight(1f),
                value = uiState.pocetStran,
                onValueChange = {
                    viewModel.setPocetStran(it) },
                labelText = stringResource(R.string.pocet_stran),
                cisla = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            InputPole(
                modifier = Modifier.weight(1f),
                value = uiState.pocetPrecitanych,
                onValueChange = {
                    viewModel.setPocetPrecitanych(it)
                },
                labelText = stringResource(R.string.pocet_precitanych),
                cisla = true
            )
        }
        Text(
            stringResource(R.string.zanre_knihy_form), Modifier
                .padding(4.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.labelLarge)
        VyberMoznosti(Zanre.entries.map { it.zaner }, vybraneZanre, onClick = {vybraneZanre[it] = !vybraneZanre[it]})
        Text(
            stringResource(R.string.vlastnosti_knihy_form), Modifier
                .padding(4.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.labelLarge)
        VyberMoznosti(Vlastnosti.entries.map {it.pridMeno}, vybraneVlastnosti, onClick = {vybraneVlastnosti[it] = !vybraneVlastnosti[it]})
        Button(modifier = Modifier.padding(top = 10.dp), onClick = onClick){
            Text(text = stringResource(R.string.ok))
        }
    }

    viewModel.setZanreVyber(vybraneZanre)
    viewModel.setVlastnostiVyber(vybraneVlastnosti)
    viewModel.setHodnotenie(hodnotenieKnihy.toString())
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VyberMoznosti(enumZoznam: List<String>, vybrane: List<Boolean>, onClick: (Int) -> Unit) {
    FlowRow(modifier = Modifier.padding(0.dp), horizontalArrangement = Arrangement.SpaceAround) {
        enumZoznam.forEachIndexed { index, _ ->
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