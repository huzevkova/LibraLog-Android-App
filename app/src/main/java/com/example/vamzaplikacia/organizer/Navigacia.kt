package com.example.vamzaplikacia.organizer

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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


@Composable
fun LibraNavHost(
    navController: NavHostController,
    viewModelFormular: FormularKnihyViewModel,
    viewModelKniha: KnihaViewModel,
    viewModelAutor: FormularAutorViewModel,
    coroutineScope: CoroutineScope,
    onVymazatKartu: () -> Unit,
    uiStateAutor: FormularAutorUIState,
    uiStateFormular: FormularKnihyUIState,
    innerPadding: PaddingValues,
    kniznica: Kniznica
) {
    NavHost(
        navController = navController,
        startDestination = LibraAppScreen.Kniznica.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = LibraAppScreen.Kniznica.name) {
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
            ZoznamKnihScreen(zoznam = Premenne.zoznamKnih, onClick = {
                Premenne.vyberKnihy = it
                navController.navigate(LibraAppScreen.VybranaKniha.name)
            })
        }
        composable(route = LibraAppScreen.Formular.name) {
            FormularKnihaScreen(viewModel = viewModelFormular, uiStateFormular) {
                val kniha = pridajZadanuKnihu(uiStateFormular, kniznica)
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
                val autor = pridajZadanehoAutora(uiState = uiStateAutor, kniznica)
                viewModelAutor.resetFormular()
                navController.popBackStack()
                coroutineScope.launch {
                    viewModelAutor.saveAutora(autor)
                }
            })
        }
    }
}
