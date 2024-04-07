package com.example.vamzaplikacia

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vamzaplikacia.grafika.formular.FormularKniha
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.Vstup
import com.example.vamzaplikacia.logika.knihy.ZoznamKnih
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme
import com.example.vamzaplikacia.grafika.zoznamiUI.VypisanieKnih
import com.example.vamzaplikacia.grafika.zoznamiUI.VytvorZoznam
import java.util.Date

val zoznamKnih = ZoznamKnih()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAMZAplikaciaTheme {
                FormularKniha()
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
        FormularKniha()
    }
}