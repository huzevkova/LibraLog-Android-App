package com.example.vamzaplikacia.organizer

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.data.AppContainer
import com.example.vamzaplikacia.grafika.formular.FormularAutorViewModel
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.formular.NovyZoznamDialog
import com.example.vamzaplikacia.grafika.formular.NovyZoznamViewModel
import com.example.vamzaplikacia.grafika.kniha.KnihaViewModel
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.grafika.formular.VymazatKartuDialog
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.organizer.pomocne_fun.LibraAppBar
import com.example.vamzaplikacia.organizer.pomocne_fun.aktualizujKnihu
import com.example.vamzaplikacia.organizer.pomocne_fun.pridajZoznam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Premenné využívané naprieč aplikáciou pre aktuálny zoznam kníh, autora a knhu
 */
object Premenne {
    var zoznamKnih = ZoznamKnih("")
    var vyberKnihy: Kniha = Kniha(nazov = "", autor = "", rokVydania = 0)
    var vyberAutora: Autor = Autor("")
}

/**
 * Hlavná funkcionalita aplikácie spúšťajúca navigáciu a ovládajúca stavy.
 *
 * @param viewModelFormular
 * @param viewModelKniha
 * @param viewModelAutor
 * @param viewModelZoznam
 * @param navController kontrolér navigácie
 * @param container kontajner s repozitármi
 * @param kniznica knižnica aplikácie
 */
@Composable
fun LibraApp(
    viewModelFormular: FormularKnihyViewModel,
    viewModelKniha: KnihaViewModel = viewModel(),
    viewModelAutor: FormularAutorViewModel,
    viewModelZoznam: NovyZoznamViewModel,
    navController: NavHostController = rememberNavController(),
    container: AppContainer,
    kniznica: Kniznica
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LibraAppScreen.valueOf(
        backStackEntry?.destination?.route ?: LibraAppScreen.Start.name
    )

    val uiStateFormular by viewModelFormular.uiState.collectAsState()
    val uiStateKniha by viewModelKniha.uiState.collectAsState()
    val uiStateAutor by viewModelAutor.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val uiStateZoznam by viewModelZoznam.uiState.collectAsState()

    var vymazatDialog by remember { mutableStateOf(false) }

    NovyZoznamDialog(viewModel = viewModelZoznam, uiState = uiStateZoznam, onClickOK = {
        val zoznam = pridajZoznam(uiStateZoznam.nazov, uiStateZoznam.obrazok, kniznica)
        viewModelZoznam.resetFormular()
        viewModelZoznam.dismissDialog()
        coroutineScope.launch {
            viewModelZoznam.saveZoznam(zoznam)
        }
    })

    if (vymazatDialog) {
        VymazatKartuDialog(
            onDismissRequest = {
                kniznica.getVsetkyZoznamy().remove(Premenne.zoznamKnih)
                vymazatDialog = false
                val policky: Flow<List<PolickaKniznice>> = container.polickyRepository.getAllItemsStream()
                coroutineScope.launch {
                    policky.collect { polickyList ->
                        polickyList.forEach { policka ->
                            if(policka.nazov == Premenne.zoznamKnih.getNazov()) {
                                container.polickyRepository.deleteItem(policka)
                            }
                        }
                    }
                }
                refresh(navController)
            },
            onConfirmation = {
                val size = Premenne.zoznamKnih.getSize()
                for (i in 0..size) {
                    kniznica.getZoznamVsetkych().odoberKnihu(Premenne.zoznamKnih.get(i))
                }
                kniznica.getVsetkyZoznamy().remove(Premenne.zoznamKnih)
                val policky: Flow<List<PolickaKniznice>> = container.polickyRepository.getAllItemsStream()
                coroutineScope.launch {
                    policky.collect { polickyList ->
                        polickyList.forEach { policka ->
                            if(policka.nazov == Premenne.zoznamKnih.getNazov()) {
                                container.polickyRepository.deleteItem(policka)
                            }
                        }
                    }
                }
                vymazatDialog = false
                refresh(navController)
            },
            dialogTitle = stringResource(R.string.zmazanie_zoznamu),
            dialogText = stringResource(R.string.zmazat_vsetky_knihy),
            icon = Icons.Filled.Warning
        )
    }

    Scaffold(
        topBar = {
            LibraAppBar(
                aktualnaObrazovka = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    if (currentScreen == LibraAppScreen.VybranaKniha) {
                        aktualizujKnihu(Premenne.vyberKnihy, uiStateKniha)
                        coroutineScope.launch {
                            container.knihyRepository.updateItem(Premenne.vyberKnihy)
                        }
                    }
                    navController.navigateUp()
                },
                navController = navController,
                container = container,
                kniznica = kniznica
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
                    Icon(Icons.Filled.Add, stringResource(R.string.tlacidlo_na_pridanie))
                }
            }
        }
    ) { innerPadding ->
        LibraNavHost(
            navController = navController,
            viewModelFormular = viewModelFormular,
            viewModelKniha = viewModelKniha,
            viewModelAutor = viewModelAutor,
            coroutineScope = coroutineScope,
            onVymazatKartu = {
                vymazatDialog = true
            },
            uiStateAutor = uiStateAutor,
            uiStateFormular = uiStateFormular,
            innerPadding = innerPadding,
            kniznica = kniznica
        )
    }
}

/**
 * Explicitiné obnovenie stavu obrazovky cez navigáciu
 *
 * @param navController
 */
fun refresh(navController: NavHostController){
    val id = navController.currentDestination?.id
    navController.popBackStack(id!!,true)
    navController.navigate(id)
}