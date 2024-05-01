package com.example.vamzaplikacia

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.logika.knihy.Kniha

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopSearchBar(onClick: (Kniha) -> Unit, onChange: (Boolean) -> Unit, onBackClick: () -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    var results by rememberSaveable { mutableStateOf(zoznamVsetkychKnih.vratPodlaPodmienky {
        it.nazov.contains(text) || it.autor.contains(text) || it.popis.contains(text) || it.poznamky.contains(text) }
    )  }

    DockedSearchBar(
        modifier = Modifier
            .padding(top = 8.dp),
        query = text,
        onQueryChange = { q ->
            text = q
            results = zoznamVsetkychKnih.vratPodlaPodmienky {
                it.nazov.lowercase().contains(text.lowercase())
            }
            if (results.size < 10) {
                results = zoznamVsetkychKnih.vratPodlaPodmienky {
                    it.nazov.lowercase().contains(text.lowercase()) || it.autor.lowercase()
                        .contains(text.lowercase())
                }
                if (results.size < 10) {
                    results = zoznamVsetkychKnih.vratPodlaPodmienky {
                        it.nazov.lowercase().contains(text.lowercase()) || it.autor.lowercase()
                            .contains(text.lowercase()) || it.popis.lowercase()
                            .contains(text.lowercase())
                    }
                }
            }
        },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            onChange(it)
            active = it },
        placeholder = { Text(text = "Hinted search text") },
        leadingIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
    {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(if (results.size > 10) 10 else results.size) { idx ->
                val resultText = results[idx].nazov
                ListItem(
                    modifier = Modifier.clickable {
                        onClick(results[idx])
                        text = resultText
                        active = false
                    },
                    headlineContent = {
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = results[idx].autor,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = null
                        )
                    },
                )
            }
        }
    }
}