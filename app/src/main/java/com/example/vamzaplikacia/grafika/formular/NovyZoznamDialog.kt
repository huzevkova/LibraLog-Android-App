package com.example.vamzaplikacia.grafika.formular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.logika.NovyZoznamUIState

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
                    Button(onClick = {}) {
                        Text("Načítaj obrázok")
                    }

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

@Preview
@Composable
fun NovyZoznamPreview() {
    NovyZoznamDialog(viewModel = NovyZoznamViewModel(), uiState = NovyZoznamUIState(), {})
}