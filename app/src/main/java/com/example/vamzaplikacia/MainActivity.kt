package com.example.vamzaplikacia

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vamzaplikacia.grafika.zoznamiUI.HlavnyZoznamKnihScreen
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAMZAplikaciaTheme {
                //LibraApp()
                MainScreen()
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
        MainScreen()
    }
}