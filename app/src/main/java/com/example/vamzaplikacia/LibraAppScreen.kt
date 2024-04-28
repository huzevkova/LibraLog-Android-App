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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vamzaplikacia.grafika.autor.AutorScreen
import com.example.vamzaplikacia.grafika.autor.AutoriZoznamScreen
import com.example.vamzaplikacia.grafika.formular.FormularKnihaScreen
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.zoznamiUI.HlavnyZoznamKnihScreen
import com.example.vamzaplikacia.grafika.kniha.KnihaScreen
import com.example.vamzaplikacia.grafika.kniha.KnihaViewModel
import com.example.vamzaplikacia.grafika.zoznamiUI.KnizicaKartyScreen
import com.example.vamzaplikacia.grafika.zoznamiUI.VedlajsiZoznamKnihScreen
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import com.example.vamzaplikacia.logika.AktualizaciaKnihyUIState
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih

var zoznamKnih = ZoznamKnih("Všetko")
var vyberKnihy: Kniha = Kniha("", "", 0)
var vyberAutora: Autor = Autor("")

enum class LibraAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Kniznica(title = R.string.kniznica),
    HlavnyZoznam(title = R.string.hlavny_zoznam),
    AutoriZoznam(title = R.string.autori_zoznam),
    VybranyAutor(title = R.string.autor),
    Formular(title = R.string.formular_knihy),
    VybranaKniha(title = R.string.kniha)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraAppBar(
    currentScreen: LibraAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit)
{
    TopAppBar(
        title = {
            if (currentScreen == LibraAppScreen.VybranaKniha){
                Text(vyberKnihy.nazov)
            } else {
                Text(stringResource(id = currentScreen.title))
            }
                },
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
                IconButton(onClick = onClick) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if (!canNavigateBack || currentScreen == LibraAppScreen.HlavnyZoznam || currentScreen == LibraAppScreen.AutoriZoznam) {
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
    viewModelFormular: FormularKnihyViewModel = viewModel(),
    viewModelKniha: KnihaViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LibraAppScreen.valueOf(
        backStackEntry?.destination?.route ?: LibraAppScreen.Start.name
    )

    val uiStateFormular by viewModelFormular.uiState.collectAsState()
    val uiStateKniha by viewModelKniha.uiState.collectAsState()

    Scaffold(
        topBar = {
            LibraAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    if (currentScreen == LibraAppScreen.VybranaKniha) {
                        AktualizujKnihu(vyberKnihy, uiStateKniha)
                    }
                    navController.navigateUp()
                },
                onClick = {
                    navController.navigate(LibraAppScreen.AutoriZoznam.name)
                }
            )
        },
        floatingActionButton = {
            if (currentScreen == LibraAppScreen.HlavnyZoznam) {
                FloatingActionButton(
                    onClick = { navController.navigate(LibraAppScreen.Formular.name) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.Add, "Tlačidlo na pridanie niečoho.")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LibraAppScreen.Kniznica.name,
            //startDestination = LibraAppScreen.VybranyAutor.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LibraAppScreen.Kniznica.name) {
                KnizicaKartyScreen(onClick = {
                    zoznamKnih = it
                    navController.navigate(LibraAppScreen.HlavnyZoznam.name)
                })
            }
            composable(route = LibraAppScreen.HlavnyZoznam.name) {
                /*HlavnyZoznamKnihScreen(onClick = {
                    vyberKnihy = it
                    navController.navigate(LibraAppScreen.VybranaKniha.name)
                })*/
                VedlajsiZoznamKnihScreen(zoznam = zoznamKnih, onClick = {
                    vyberKnihy = it
                    navController.navigate(LibraAppScreen.VybranaKniha.name)
            })
            }
            composable(route = LibraAppScreen.Formular.name) {
                FormularKnihaScreen(viewModel = viewModelFormular, uiStateFormular, onClick = {
                    PridajZadanuKnihu(uiState = uiStateFormular)
                    viewModelFormular.resetFormular()
                    navController.popBackStack()
                })
            }
            composable(route = LibraAppScreen.VybranaKniha.name) {
                KnihaScreen(vyberKnihy, viewModelKniha)
            }
            composable(route = LibraAppScreen.AutoriZoznam.name) {
                AutoriZoznamScreen(onClick = {
                    vyberAutora = it
                    navController.navigate(LibraAppScreen.VybranyAutor.name)
                })
            }
            composable(route = LibraAppScreen.VybranyAutor.name) {
                AutorScreen(autor = vyberAutora, onClick = {
                    vyberKnihy = it
                    navController.navigate(LibraAppScreen.VybranaKniha.name)
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

fun AktualizujKnihu(kniha: Kniha, uiState: AktualizaciaKnihyUIState) {
    kniha.setHodnotenie(uiState.hodnotenie)
    kniha.setPrecitaneStrany(uiState.pocetPrecitanych)
}