package com.example.vamzaplikacia.organizer

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.grafika.autor.AutorScreen
import com.example.vamzaplikacia.grafika.autor.AutoriZoznamScreen
import com.example.vamzaplikacia.grafika.formular.FormularAutorScreen
import com.example.vamzaplikacia.grafika.formular.FormularAutorViewModel
import com.example.vamzaplikacia.grafika.formular.FormularKnihaScreen
import com.example.vamzaplikacia.grafika.formular.FormularKnihyViewModel
import com.example.vamzaplikacia.grafika.kniha.KnihaScreen
import com.example.vamzaplikacia.grafika.kniha.KnihaViewModel
import com.example.vamzaplikacia.grafika.zoznami.KnizicaKartyScreen
import com.example.vamzaplikacia.grafika.zoznami.ZoznamKnihScreen
import com.example.vamzaplikacia.grafika.UIState.FormularAutorUIState
import com.example.vamzaplikacia.grafika.UIState.FormularKnihyUIState
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.organizer.pomocne_fun.pridajZadanehoAutora
import com.example.vamzaplikacia.organizer.pomocne_fun.pridajZadanuKnihu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Enum možných cieľov pre navigáciu
 *
 * @param title nazov obrazovky
 */
enum class LibraAppScreen(@StringRes val title: Int) {
    Start(title = R.string.kniznica),
    HlavnyZoznam(title = R.string.hlavny_zoznam),
    AutoriZoznam(title = R.string.autori_zoznam),
    VybranyAutor(title = R.string.autor),
    Formular(title = R.string.formular_knihy),
    FormularAutor(title = R.string.novy_autor),
    VybranaKniha(title = R.string.kniha)
}

/**
 * Navigácia aplikácie, využívajúca NavHostController na presúvanie sa medzi obrazovkami
 *
 * @param navController
 * @param viewModelFormular
 * @param viewModelKniha
 * @param viewModelAutor
 * @param coroutineScope korutina
 * @param onVymazatKartu funkcia ktorá sa vykoná pri zmazaní karty
 * @param uiStateAutor stav autora
 * @param uiStateFormular stav knihy
 * @param innerPadding padding ktorý sa má použiť
 * @param kniznica knižnica aplikácia
 */
@Composable
fun LibraNavHost(
    navController: NavHostController,
    viewModelFormular: FormularKnihyViewModel,
    viewModelKniha: KnihaViewModel,
    viewModelAutor: FormularAutorViewModel,
    coroutineScope: CoroutineScope,
    onVymazatKartu: () -> Unit,
    onLongClick: () -> Unit,
    uiStateAutor: FormularAutorUIState,
    uiStateFormular: FormularKnihyUIState,
    innerPadding: PaddingValues,
    kniznica: Kniznica
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = LibraAppScreen.Start.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = LibraAppScreen.Start.name) {
            KnizicaKartyScreen(
                onClick = {
                    Premenne.zoznamKnih = it
                    navController.navigate(LibraAppScreen.HlavnyZoznam.name)
                },
                onDeleteClick = {
                    Premenne.zoznamKnih = it
                    onVymazatKartu()
                },
                kniznica = kniznica
            )
        }
        composable(route = LibraAppScreen.HlavnyZoznam.name) {
            ZoznamKnihScreen(zoznam = Premenne.zoznamKnih,
                onClick = {
                Premenne.vyberKnihy = it
                navController.navigate(LibraAppScreen.VybranaKniha.name)
                },
                onLongClick = {
                    it.favorit = !it.favorit
                    Premenne.vyberKnihy = it
                    if (it.favorit) {
                        kniznica.getZoznamOblubenych().pridajKnihu(it)
                    } else {
                        kniznica.getZoznamOblubenych().odoberKnihu(it)
                    }
                    onLongClick()
                })
        }
        composable(route = LibraAppScreen.Formular.name) {
            FormularKnihaScreen(viewModel = viewModelFormular, uiStateFormular) {
                val kniha = pridajZadanuKnihu(uiStateFormular, kniznica, context)
                viewModelFormular.resetFormular()
                navController.popBackStack()
                coroutineScope.launch {
                    viewModelFormular.saveKniha(kniha)
                }
            }
        }
        composable(route = LibraAppScreen.VybranaKniha.name) {
            KnihaScreen(Premenne.vyberKnihy, viewModelKniha)
        }
        composable(route = LibraAppScreen.AutoriZoznam.name) {
            AutoriZoznamScreen(kniznica.getZoznamAutorov(), onClick = {
                Premenne.vyberAutora = it
                navController.navigate(LibraAppScreen.VybranyAutor.name)
            })
        }
        composable(route = LibraAppScreen.VybranyAutor.name) {
            Premenne.vyberAutora.nastavKnihy(kniznica.getZoznamVsetkych())
            AutorScreen(autor = Premenne.vyberAutora, onClick = {
                Premenne.vyberKnihy = it
                navController.navigate(LibraAppScreen.VybranaKniha.name)
            })
        }
        composable(route = LibraAppScreen.FormularAutor.name) {
            FormularAutorScreen(viewModel = viewModelAutor, uiState = uiStateAutor, onClick = {
                val autor = pridajZadanehoAutora(uiState = uiStateAutor, kniznica, context)
                viewModelAutor.resetFormular()
                navController.popBackStack()
                coroutineScope.launch {
                    viewModelAutor.saveAutora(autor)
                }
            })
        }
    }
}
