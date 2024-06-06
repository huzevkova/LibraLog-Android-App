package com.example.vamzaplikacia

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vamzaplikacia.data.AppContainer
import com.example.vamzaplikacia.grafika.autor.AutorScreen
import com.example.vamzaplikacia.grafika.autor.AutoriZoznamScreen
import com.example.vamzaplikacia.grafika.autor.autori
import com.example.vamzaplikacia.grafika.formular.FormularAutorScreen
import com.example.vamzaplikacia.grafika.formular.FormularAutorViewModel
import com.example.vamzaplikacia.grafika.formular.FormularKnihaScreen
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.formular.NovyZoznamDialog
import com.example.vamzaplikacia.grafika.formular.NovyZoznamViewModel
import com.example.vamzaplikacia.grafika.kniha.KnihaScreen
import com.example.vamzaplikacia.grafika.kniha.KnihaViewModel
import com.example.vamzaplikacia.grafika.zoznamiUI.KnizicaKartyScreen
import com.example.vamzaplikacia.grafika.zoznamiUI.ZoznamKnihScreen
import com.example.vamzaplikacia.grafika.zoznamiUI.kniznica
import com.example.vamzaplikacia.logika.AktualizaciaKnihyUIState
import com.example.vamzaplikacia.logika.FormularAutorUIState
import com.example.vamzaplikacia.logika.FormularKnihyUIState
import com.example.vamzaplikacia.logika.enumy.Vlastnosti
import com.example.vamzaplikacia.logika.enumy.Zanre
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.grafika.formular.VymazatKartuDialog
import kotlinx.coroutines.launch

var zoznamVsetkychKnih = ZoznamKnih("Všetko")
var zoznamKnih = zoznamVsetkychKnih
var vyberKnihy: Kniha = Kniha(nazov = "", autor = "", rokVydania = 0)
var vyberAutora: Autor = Autor("")

enum class LibraAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Kniznica(title = R.string.kniznica),
    HlavnyZoznam(title = R.string.hlavny_zoznam),
    AutoriZoznam(title = R.string.autori_zoznam),
    VybranyAutor(title = R.string.autor),
    Formular(title = R.string.formular_knihy),
    FormularAutor(title = R.string.novy_autor),
    VybranaKniha(title = R.string.kniha)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraAppBar(
    currentScreen: LibraAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavHostController,
    container: AppContainer
)
{
    var showDropDownMenuLeft by remember { mutableStateOf(false) }
    var showDropDownMenuRight by remember { mutableStateOf(false) }
    var searchEnable by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    if (searchEnable) {
        TopSearchBar(onClick = {
            searchEnable = false
            vyberKnihy = it
            navController.navigate(LibraAppScreen.VybranaKniha.name) },
            onChange = {searchEnable = it},
            onBackClick = {
                navigateUp()
                searchEnable = false
            }
        )
    } else {
        TopAppBar(
            title = {
                if (currentScreen == LibraAppScreen.VybranaKniha) {
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
                    IconButton(onClick = { showDropDownMenuLeft = true }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
                DropdownMenu(
                    showDropDownMenuLeft, { showDropDownMenuLeft = false }
                ) {
                    DropdownMenuItem(text = { Text(text = "Obľúbení autori") }, leadingIcon = {
                        Icon(
                            Icons.Filled.Person, contentDescription = "autori"
                        )
                    }, onClick = {
                        navController.navigate(LibraAppScreen.AutoriZoznam.name)
                        showDropDownMenuLeft = false
                    })
                }
            },
            actions = {
                if (!canNavigateBack || currentScreen == LibraAppScreen.HlavnyZoznam || currentScreen == LibraAppScreen.AutoriZoznam) {
                    IconButton(onClick = { searchEnable = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
                IconButton(
                    onClick = { showDropDownMenuRight = true}
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Sort"
                    )
                }
                DropdownMenu(
                    showDropDownMenuRight, { showDropDownMenuRight = false }
                ) {
                    var showNestedMenuAdd by remember { mutableStateOf(false) }
                    var showNestedMenuRemove by remember { mutableStateOf(false) }
                    if (!showNestedMenuAdd && !showNestedMenuRemove) {
                        DropdownMenuItem(
                            text = { Text(text = "Pridaj do zoznamu...") }, leadingIcon = {
                                Icon(
                                    Icons.Filled.Add, contentDescription = "pridat do zoznamu"
                                )
                            }, onClick = {
                                showNestedMenuAdd = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Zmaž knihu...") }, leadingIcon = {
                                Icon(
                                    Icons.Filled.Delete, contentDescription = "zmazať"
                                )
                            }, onClick = {
                                showNestedMenuRemove = true
                            }
                        )
                    } else if (showNestedMenuAdd){
                        for (zoznam in kniznica.KniznicaIterator()) {
                            DropdownMenuItem(
                                text = { Text(text = zoznam.getNazov()) }, leadingIcon = {
                                    Icon(
                                        Icons.Filled.Add, contentDescription = "pridat do zoznamu"
                                    )
                                }, onClick = {
                                    zoznam.pridajKnihu(vyberKnihy)
                                    showNestedMenuAdd = false
                                    showDropDownMenuRight = false
                                }
                            )
                        }
                    } else {
                        DropdownMenuItem(
                            text = { Text(text = "Zmaž všade") }, leadingIcon = {
                                Icon(
                                    Icons.Filled.Add, contentDescription = "zmaz vsade"
                                )
                            }, onClick = {
                                zoznamVsetkychKnih.odoberKnihu(vyberKnihy)
                                showNestedMenuRemove = false
                                showDropDownMenuRight = false
                                coroutineScope.launch {
                                    container.knihyRepository.deleteItem(vyberKnihy)
                                }
                                navigateUp()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Zmaž v tomto zozname") }, leadingIcon = {
                                Icon(
                                    Icons.Filled.Delete, contentDescription = "zmaz v zozname"
                                )
                            }, onClick = {
                                zoznamKnih.odoberKnihu(vyberKnihy)
                                showNestedMenuRemove = false
                                showDropDownMenuRight = false
                                coroutineScope.launch {
                                    container.knihyRepository.deleteItem(vyberKnihy)
                                }
                                navigateUp()
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun LibraApp(
    viewModelFormular: FormularKnihyViewModel,
    viewModelKniha: KnihaViewModel = viewModel(),
    viewModelAutor: FormularAutorViewModel = viewModel(),
    viewModelZoznam: NovyZoznamViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    container: AppContainer
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LibraAppScreen.valueOf(
        backStackEntry?.destination?.route ?: LibraAppScreen.Start.name
    )

    val uiStateFormular by viewModelFormular.uiState.collectAsState()
    val uiStateKniha by viewModelKniha.uiState.collectAsState()
    val uiStateAutor by viewModelAutor.uiState.collectAsState()


    val uiStateZoznam by viewModelZoznam.uiState.collectAsState()
    NovyZoznamDialog(viewModel = viewModelZoznam, uiState = uiStateZoznam, onClickOK = {
        pridajZoznam(uiStateZoznam.nazov, uiStateZoznam.obrazok)
        viewModelZoznam.resetFormular()
        viewModelZoznam.dismissDialog()
    }
    )

    var vymazatDialog by remember { mutableStateOf(false) }
    if (vymazatDialog) {
        VymazatKartuDialog(
            onDismissRequest = {
                kniznica.getZoznam().remove(zoznamKnih)
                vymazatDialog = false
                refresh(navController)
            },
            onConfirmation = {
                val size = zoznamKnih.getSize()
                for (i in 0..size) {
                    zoznamVsetkychKnih.odoberKnihu(zoznamKnih.get(i))
                }
                kniznica.getZoznam().remove(zoznamKnih)
                vymazatDialog = false
                refresh(navController)
            },
            dialogTitle = "Zmazanie zoznamu",
            dialogText = "Chcete zmazať aj všetky knihy v zozname?",
            icon = Icons.Filled.Warning
        )
    }

    Scaffold(
        topBar = {
            LibraAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    if (currentScreen == LibraAppScreen.VybranaKniha) {
                        aktualizujKnihu(vyberKnihy, uiStateKniha)
                    }
                    navController.navigateUp()
                },
                navController = navController,
                container = container
            )
        },
        floatingActionButton = {
            if (currentScreen == LibraAppScreen.HlavnyZoznam || currentScreen == LibraAppScreen.AutoriZoznam || currentScreen == LibraAppScreen.Kniznica) {
                FloatingActionButton(
                    onClick = {
                        when (currentScreen) {
                            LibraAppScreen.AutoriZoznam -> {
                                navController.navigate(LibraAppScreen.FormularAutor.name)
                            }
                            LibraAppScreen.HlavnyZoznam -> {
                                navController.navigate(LibraAppScreen.Formular.name)
                            }
                            else -> {
                                viewModelZoznam.openDialog()
                            }
                        }
                    },
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
            //startDestination = LibraAppScreen.FormularAutor.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LibraAppScreen.Kniznica.name) {
                KnizicaKartyScreen(
                    onClick = {
                    zoznamKnih = it
                    navController.navigate(LibraAppScreen.HlavnyZoznam.name)
                },
                    onDeleteClick = {
                        vymazatDialog = true
                    }
                )
            }
            composable(route = LibraAppScreen.HlavnyZoznam.name) {
                ZoznamKnihScreen(zoznam = zoznamKnih, onClick = {
                    vyberKnihy = it
                    navController.navigate(LibraAppScreen.VybranaKniha.name)
                })
            }
            composable(route = LibraAppScreen.Formular.name) {
                FormularKnihaScreen(viewModel = viewModelFormular, uiStateFormular) { navController.popBackStack() }
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
            composable(route = LibraAppScreen.FormularAutor.name) {
                FormularAutorScreen(viewModel = viewModelAutor, uiState = uiStateAutor, onClick = {
                    pridajZadanehoAutora(uiState = uiStateAutor)
                    viewModelAutor.resetFormular()
                    navController.popBackStack()
                })
            }
        }
    }
}

fun pridajZadanuKnihu(uiState: FormularKnihyUIState): Kniha {
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

    val rok: Int = if(uiState.rok.toIntOrNull()==null) 0 else uiState.rok.toInt()
    val pocetStran: Int = if(uiState.pocetStran.toIntOrNull()==null) 0 else uiState.pocetStran.toInt()
    val pocetPrecitanych: Int = if(uiState.pocetPrecitanych.toIntOrNull()==null) 0 else uiState.pocetPrecitanych.toInt()
    val hodnotenie: Double = if(uiState.hodnotenie.toDoubleOrNull()==null) 0.0 else uiState.hodnotenie.toDouble()

    val kniha = Kniha(uiState.nazov, uiState.autor, rok, uiState.vydavatelstvo,
        uiState.obrazok, uiState.popis, uiState.poznamky, uiState.precitana, uiState.naNeskor,
        uiState.pozicana, uiState.kupena, pocetStran, pocetPrecitanych, hodnotenie)
    kniha.zanre = zanre
    kniha.vlastnosti = vlastnosti
    zoznamVsetkychKnih.pridajKnihu(kniha)
    zoznamKnih.pridajKnihu(kniha)
    return kniha
}

fun pridajZadanehoAutora(uiState: FormularAutorUIState) {
    val autor = Autor(uiState.menoAutora, uiState.datumNar, uiState.datumUmrtia, obrazokCesta = uiState.obrazok)
    autor.popis = uiState.popis
    autori.pridajAutora(autor)
}

fun aktualizujKnihu(kniha: Kniha, uiState: AktualizaciaKnihyUIState) {
    kniha.hodnotenie = uiState.hodnotenie
    kniha.pocetPrecitanych = uiState.pocetPrecitanych
}

fun pridajZoznam(nazov: String, obrazok: Uri? = null) {
    kniznica.getZoznam().add(ZoznamKnih(nazov, obrazok = obrazok))
}

fun refresh(navController: NavHostController){
    val id = navController.currentDestination?.id
    navController.popBackStack(id!!,true)
    navController.navigate(id)
}
