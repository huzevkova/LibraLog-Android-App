package com.example.vamzaplikacia.organizer.pomocne_fun

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.vamzaplikacia.R
import com.example.vamzaplikacia.data.AppContainer
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.organizer.LibraAppScreen
import com.example.vamzaplikacia.organizer.Premenne
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraAppBar(
    aktualnaObrazovka: LibraAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavHostController,
    container: AppContainer,
    kniznica: Kniznica
) {
    var otvorLaveDropDownMenu by remember { mutableStateOf(false) }
    var otvorPraveDropDownMenu by remember { mutableStateOf(false) }
    var vyhladavanieEnable by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if (vyhladavanieEnable) {
        TopSearchBar(onClick = {
            vyhladavanieEnable = false
            Premenne.vyberKnihy = it
            navController.navigate(LibraAppScreen.VybranaKniha.name)
        }, onChange = { vyhladavanieEnable = it },
            onBackClick = {
                navigateUp()
                vyhladavanieEnable = false
            },
            kniznica = kniznica)
    } else {
        TopAppBar(
            title = { AppBarTitle(aktualnaObrazovka) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            navigationIcon = {
                AppBarNavigationIcon(
                    canNavigateBack = canNavigateBack,
                    navigateUp = navigateUp,
                    otvorLaveDropDownMenu = otvorLaveDropDownMenu,
                    setOtvorLaveDropDownMenu = { otvorLaveDropDownMenu = it },
                    navController = navController
                )
            },
            actions = {
                AppBarActions(
                    aktualnaObrazovka = aktualnaObrazovka,
                    canNavigateBack = canNavigateBack,
                    vyhladavanieEnable = vyhladavanieEnable,
                    setVyhladavanieEnable = { vyhladavanieEnable = it },
                    otvorPraveDropDownMenu = otvorPraveDropDownMenu,
                    setOtvorPraveDropDownMenu = { otvorPraveDropDownMenu = it },
                    navController = navController,
                    navigateUp = navigateUp,
                    coroutineScope = coroutineScope,
                    container = container,
                    kniznica = kniznica
                )
            }
        )
    }
}

@Composable
fun AppBarTitle(aktualnaObrazovka: LibraAppScreen) {
    if (aktualnaObrazovka == LibraAppScreen.VybranaKniha) {
        Text(Premenne.vyberKnihy.nazov)
    } else {
        Text(stringResource(id = aktualnaObrazovka.title))
    }
}

@Composable
fun AppBarNavigationIcon(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    otvorLaveDropDownMenu: Boolean,
    setOtvorLaveDropDownMenu: (Boolean) -> Unit,
    navController: NavHostController
) {
    if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_button)
            )
        }
    } else {
        IconButton(onClick = { setOtvorLaveDropDownMenu(true) }) {
            Icon(Icons.Filled.Menu, contentDescription = stringResource(R.string.menu))
        }
    }
    DropdownMenu(
        otvorLaveDropDownMenu, { setOtvorLaveDropDownMenu(false) }
    ) {
        DropdownMenuItem(text = { Text(text = stringResource(R.string.oblubeni_autori)) }, leadingIcon = {
            Icon(Icons.Filled.Person, contentDescription = stringResource(R.string.autori))
        }, onClick = {
            navController.navigate(LibraAppScreen.AutoriZoznam.name)
            setOtvorLaveDropDownMenu(false)
        })
    }
}

@Composable
fun AppBarActions(
    aktualnaObrazovka: LibraAppScreen,
    canNavigateBack: Boolean,
    vyhladavanieEnable: Boolean,
    setVyhladavanieEnable: (Boolean) -> Unit,
    otvorPraveDropDownMenu: Boolean,
    setOtvorPraveDropDownMenu: (Boolean) -> Unit,
    navController: NavHostController,
    navigateUp: () -> Unit,
    coroutineScope: CoroutineScope,
    container: AppContainer,
    kniznica: Kniznica
) {
    if (!canNavigateBack || aktualnaObrazovka == LibraAppScreen.HlavnyZoznam || aktualnaObrazovka == LibraAppScreen.AutoriZoznam) {
        IconButton(onClick = { setVyhladavanieEnable(true) }) {
            Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.vyhladavanie))
        }
    }
    IconButton(onClick = { setOtvorPraveDropDownMenu(true) }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Sort")
    }
    DropdownMenu(
        otvorPraveDropDownMenu, { setOtvorPraveDropDownMenu(false) }
    ) {
        AppBarDropdownItems(
            aktualnaObrazovka = aktualnaObrazovka,
            setOtvorPraveDropDownMenu = setOtvorPraveDropDownMenu,
            navController = navController,
            navigateUp = navigateUp,
            coroutineScope = coroutineScope,
            container = container,
            kniznica = kniznica
        )
    }
}

@Composable
fun AppBarDropdownItems(
    aktualnaObrazovka: LibraAppScreen,
    setOtvorPraveDropDownMenu: (Boolean) -> Unit,
    navController: NavHostController,
    navigateUp: () -> Unit,
    coroutineScope: CoroutineScope,
    container: AppContainer,
    kniznica: Kniznica
) {
    var otvorPridajNestedMenu by remember { mutableStateOf(false) }
    var otvorOdstranNestedMenu by remember { mutableStateOf(false) }

    if (!otvorPridajNestedMenu && !otvorOdstranNestedMenu) {
        if (aktualnaObrazovka == LibraAppScreen.VybranaKniha) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.pridaj_do_zoznamu)) }, leadingIcon = {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.pridaj_do_zoznamu))
                }, onClick = { otvorPridajNestedMenu = true }
            )
        }
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.zmaz)) }, leadingIcon = {
                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.zmazanie))
            }, onClick = {
                if (aktualnaObrazovka == LibraAppScreen.VybranyAutor) {
                    kniznica.odoberAutora(Premenne.vyberAutora)
                    coroutineScope.launch {
                        container.autoriRepository.deleteItem(Premenne.vyberAutora)
                    }
                    navigateUp()
                } else {
                    otvorOdstranNestedMenu = true
                }
            }
        )
    } else if (otvorPridajNestedMenu) {
        for (zoznam in kniznica.KniznicaIterator()) {
            DropdownMenuItem(
                text = { Text(text = zoznam.getNazov()) }, leadingIcon = {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.pridaj_do_zoznamu))
                }, onClick = {
                    zoznam.pridajKnihu(Premenne.vyberKnihy)
                    otvorPridajNestedMenu = false
                    setOtvorPraveDropDownMenu(false)
                }
            )
        }
    } else {
        if (aktualnaObrazovka == LibraAppScreen.VybranaKniha) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.zmas_vsade)) }, leadingIcon = {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.zmas_vsade))
                }, onClick = {
                    kniznica.getZoznamVsetkych().odoberKnihu(Premenne.vyberKnihy)
                    otvorOdstranNestedMenu = false
                    setOtvorPraveDropDownMenu(false)
                    coroutineScope.launch {
                        container.knihyRepository.deleteItem(Premenne.vyberKnihy)
                    }
                    navigateUp()
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.zmaz_v_tomto_zozname)) }, leadingIcon = {
                    Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.zmaz_v_tomto_zozname))
                }, onClick = {
                    Premenne.zoznamKnih.odoberKnihu(Premenne.vyberKnihy)
                    otvorOdstranNestedMenu = false
                    setOtvorPraveDropDownMenu(false)
                    coroutineScope.launch {
                        container.knihyRepository.deleteItem(Premenne.vyberKnihy)
                    }
                    navigateUp()
                }
            )
        }
    }
}