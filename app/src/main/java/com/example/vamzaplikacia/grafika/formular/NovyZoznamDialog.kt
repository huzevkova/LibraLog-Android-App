package com.example.vamzaplikacia.grafika.formular

import VyberObrazkuButton
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
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.grafika.UIState.NovyZoznamUIState

@Composable
fun NovyZoznamDialog(
    viewModel: NovyZoznamViewModel,
    uiState: NovyZoznamUIState,
    onClickOK: () -> Unit
) {
    if (uiState.showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Nový zoznam") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = uiState.nazov,
                        onValueChange = { viewModel.setNazov(it) },
                        label = { Text(text = "Názov zoznamu") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    VyberObrazkuButton{viewModel.setObrazok(it)}
                }
            },
            confirmButton = {
                Button(onClick = onClickOK) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.dismissDialog() }) {
                    Text("Cancel")
                }
            }
        )
    }
}

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
            Icon(icon, contentDescription = "Example Icon")
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
                Text("Áno, vymaž aj knihy.")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Nie, vymaž len zoznam.")
            }
        }
    )
}
