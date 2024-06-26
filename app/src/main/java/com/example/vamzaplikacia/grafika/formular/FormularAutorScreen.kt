package com.example.vamzaplikacia.grafika.formular

import com.example.vamzaplikacia.organizer.pomocne_fun.VyberObrazkuButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.grafika.UIState.FormularAutorUIState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import com.example.vamzaplikacia.R

/**
 * Obrazovka pre formulár na zadanie nového autora - meno, roky, obrázok.
 *
 * @param viewModel viewModel ktorého sa to týka
 * @param uiState stav formuláru
 * @param onClick funkcia čo sa vykoná po kliknutí
 */
@Composable
fun FormularAutorScreen(viewModel: FormularAutorViewModel, uiState: FormularAutorUIState, onClick: () -> Unit) {

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        InputPole(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = uiState.menoAutora,
            placeHolder = stringResource(id = R.string.autor_placeholder),
            onValueChange = { viewModel.setMeno(it) },
            labelText = stringResource(R.string.meno_autora)
        )

        DatumTextField(viewModel = viewModel, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), true)
        DatumTextField(viewModel = viewModel, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), false)

        VyberObrazkuButton {
            viewModel.setObrazok(it)
        }

        Spacer(modifier = Modifier.height(8.dp))
        InputPole(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = uiState.popis,
            onValueChange = { viewModel.setPopis(it) },
            labelText = stringResource(R.string.popis)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.padding(top = 10.dp), onClick = onClick){
            Text(text = stringResource(id = R.string.ok))
        }
    }
}

/**
 * TextField na zadanie dátumu narodenia alebo úmrtia
 *
 * @param viewModel viewModel ktorého sa to týka
 * @param modifier modifikátor
 * @param narodenie či ide o narodenie alebo nie (úmrtie)
 */
@Composable
fun DatumTextField(viewModel: FormularAutorViewModel, modifier: Modifier, narodenie: Boolean) {
    val keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    )
    var textValue by remember { mutableStateOf(TextFieldValue()) }

    TextField(
        modifier = modifier,
        value = textValue,
        placeholder = { Text(text = stringResource(R.string.datum_format), color = Color.Gray) },
        label = { if (narodenie) {
                    Text(stringResource(R.string.datum_narodenia))
                } else {
                    Text(stringResource(R.string.datum_umrtia))
                }
        },
        onValueChange = { newInput ->
            textValue = if (newInput.text.length < textValue.text.length) {
                newInput.copy(
                    text = newInput.text,
                    selection = newInput.selection
                )
            } else {
                val newValue = formatText(newInput.text)

                newInput.copy(
                    text = newValue,
                    selection = TextRange(newValue.length)
                )
            }
            if (narodenie) {
                viewModel.setNarodenie(textValue.text)
            } else {
                viewModel.setUmrtie(textValue.text)
            }
        },
        keyboardOptions = keyboardOptions
    )

}

/**
 * Funkcia na formátovanie dátumového textu
 *
 * @param text text na formátovanie
 */
private fun formatText(text: String): String {
    val maxLength = 10 // DD.MM.YYYY

    if (text.length > maxLength) {
        return text.substring(0, maxLength)
    }

    var result = text


    if (text.length == 2) {
        result += "."
        return result
    }

    if (text.length == 5) {
        result += "."
        return result
    }

    return result
}