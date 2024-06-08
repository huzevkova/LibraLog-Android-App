package com.example.vamzaplikacia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.vamzaplikacia.data.AppDataContainer
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var container = AppDataContainer(this)
        var kniznica = Kniznica(applicationContext)

        setContent {
            VAMZAplikaciaTheme {
                SpustenieProgramu(container, kniznica)
            }
        }
    }
}
