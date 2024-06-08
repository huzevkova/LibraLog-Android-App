package com.example.vamzaplikacia.organizer.pomocne_fun

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
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.Kniznica

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopSearchBar(onClick: (Kniha) -> Unit, onChange: (Boolean) -> Unit, onBackClick: () -> Unit, kniznica: Kniznica) {
    var text by rememberSaveable { mutableStateOf("") }
    var aktivny by rememberSaveable { mutableStateOf(false) }

    val zoznamVsetkychKnih = kniznica.getZoznamVsetkych()
    var vysledky by rememberSaveable { mutableStateOf(zoznamVsetkychKnih.vratPodlaPodmienky {
        it.nazov.contains(text) || it.autor.contains(text) || it.popis.contains(text) || it.poznamky.contains(text) }
    )  }

    DockedSearchBar(
        modifier = Modifier
            .padding(top = 8.dp),
        query = text,
        onQueryChange = { q ->
            text = q
            vysledky = zoznamVsetkychKnih.vratPodlaPodmienky {
                it.nazov.lowercase().contains(text.lowercase())
            }
            if (vysledky.size < 10) {
                vysledky = zoznamVsetkychKnih.vratPodlaPodmienky {
                    it.nazov.lowercase().contains(text.lowercase()) || it.autor.lowercase()
                        .contains(text.lowercase())
                }
                if (vysledky.size < 10) {
                    vysledky = zoznamVsetkychKnih.vratPodlaPodmienky {
                        it.nazov.lowercase().contains(text.lowercase()) || it.autor.lowercase()
                            .contains(text.lowercase()) || it.popis.lowercase()
                            .contains(text.lowercase())
                    }
                }
            }
        },
        onSearch = { aktivny = false },
        active = aktivny,
        onActiveChange = {
            onChange(it)
            aktivny = it },
        placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
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
            items(if (vysledky.size > 10) 10 else vysledky.size) { idx ->
                val resultText = vysledky[idx].nazov
                ListItem(
                    modifier = Modifier.clickable {
                        onClick(vysledky[idx])
                        text = resultText
                        aktivny = false
                    },
                    headlineContent = {
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = vysledky[idx].autor,
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