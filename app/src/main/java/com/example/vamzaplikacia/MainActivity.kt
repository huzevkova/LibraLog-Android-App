package com.example.vamzaplikacia

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.vamzaplikacia.data.AppDataContainer
import com.example.vamzaplikacia.grafika.zoznamiUI.kniznica
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var container = AppDataContainer(this)
        val knihy: Flow<List<Kniha>> = container.knihyRepository.getAllItemsStream()

        kniznica.pridajZoznam(ZoznamKnih("Všetko"))
        lifecycleScope.launch {
            knihy.collect { knihyList ->
                knihyList.forEach { kniha ->
                    kniznica.getZoznam()[0].pridajKnihu(kniha)
                }
            }
        }
        setContent {
            VAMZAplikaciaTheme {
                MainScreen(container)
            }
        }
    }
}


//@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun AppPreview() {
    VAMZAplikaciaTheme {
        //MainScreen()
    }
}