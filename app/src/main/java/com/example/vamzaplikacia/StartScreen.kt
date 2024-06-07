package com.example.vamzaplikacia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.data.AppContainer
import com.example.vamzaplikacia.grafika.formular.FormularAutorViewModel
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.formular.NovyZoznamViewModel
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.libralog),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}

private var start = true

@Composable
fun MainScreen(container: AppContainer) {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }

    if (start) {
        if (isLoading) {
            LoadingScreen()
        } else {
            start = false
            LibraApp(
                viewModelFormular = FormularKnihyViewModel(container.knihyRepository),
                viewModelAutor = FormularAutorViewModel(container.autoriRepository),
                viewModelZoznam = NovyZoznamViewModel(container.polickyRepository),
                container = container)
        }
    } else {
        LibraApp(
            viewModelFormular = FormularKnihyViewModel(container.knihyRepository),
            viewModelAutor = FormularAutorViewModel(container.autoriRepository),
            viewModelZoznam = NovyZoznamViewModel(container.polickyRepository),
            container = container)
    }
}