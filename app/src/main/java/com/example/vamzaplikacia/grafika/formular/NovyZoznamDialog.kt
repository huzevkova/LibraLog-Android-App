package com.example.vamzaplikacia.grafika.formular

import com.example.vamzaplikacia.organizer.pomocne_fun.VyberObrazkuButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.grafika.UIState.NovyZoznamUIState

/**
 * Dialógové okno na pridanie nového zoznamu
 *
 * @param viewModel viewModel ktorého sa to týka
 * @param uiState stav formuláru
 * @param onClickOK funkcia čo sa vykoná po kliknutí OK
 */
@Composable
fun NovyZoznamDialog(
    viewModel: NovyZoznamViewModel,
    uiState: NovyZoznamUIState,
    onClickOK: () -> Unit
) {
    if (uiState.showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = stringResource(R.string.novy_zoznam)) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = uiState.nazov,
                        onValueChange = { viewModel.setNazov(it) },
                        label = { Text(text = stringResource(R.string.nazov_zoznamu)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    VyberObrazkuButton{viewModel.setObrazok(it)}
                }
            },
            confirmButton = {
                Button(onClick = onClickOK) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.dismissDialog() }) {
                    Text(stringResource(R.string.zrus))
                }
            }
        )
    }
}

/**
 * Dialóg na zmazanie karty
 *
 * @param onDismissRequest funkcia čo sa vykoná po odmietnutí
 * @param onConfirmation funkcia čo sa vykoná po potvrdení
 * @param dialogTitle nadpis dialógu
 * @param dialogText text dialógu
 * @param icon obrázok dialógu
 */
@Composable
fun VymazatKartuDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = stringResource(R.string.ikona_pozor))
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.vymaz_aj_knihy))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.vymaz_len_zoznam))
            }
        }
    )
}
