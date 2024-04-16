package com.example.vamzaplikacia

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vamzaplikacia.grafika.formular.FormularKnihaScreen
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.zoznamiUI.HlavnyZoznamKnihScreen
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

val zoznamKnih = ZoznamKnih()

enum class LibraAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    HlavnyZoznam(title = R.string.hlavny_zoznam),
    Formular(title = R.string.formular_knihy)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraAppBar(
    currentScreen: LibraAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier)
{
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            } else {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if (!canNavigateBack) {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Sort"
                    )
                }
            }
        }

    )
}

@Composable
fun LibraApp(
    viewModel: FormularKnihyViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LibraAppScreen.valueOf(
        backStackEntry?.destination?.route ?: LibraAppScreen.Start.name
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            LibraAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(LibraAppScreen.Formular.name)},
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "Tlačidlo na pridanie niečoho.")
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LibraAppScreen.HlavnyZoznam.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LibraAppScreen.HlavnyZoznam.name) {
                HlavnyZoznamKnihScreen()
            }
            composable(route = LibraAppScreen.Formular.name) {
                FormularKnihaScreen(viewModel = viewModel, uiState, onClick = {
                    PridajZadanuKnihu(uiState = uiState)
                    viewModel.resetFormular()
                    navController.popBackStack()
                })
            }
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