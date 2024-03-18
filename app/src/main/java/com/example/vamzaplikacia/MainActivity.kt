package com.example.vamzaplikacia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme
import java.util.Date

val zoznamKnih = ZoznamKnih()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAMZAplikaciaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VytvorZoznam()
                    VypisanieKnih(zoznam = zoznamKnih)
                }
            }
        }
    }
}

fun VytvorZoznam() {
    zoznamKnih.pridajKnihu(Kniha("Veľký Gatsby", "F. Scott Fitzgerald", 1925))
    zoznamKnih.pridajKnihu(Kniha("Alchymista", "Paulo Coelho", 1988, Date(2000, 2, 2)))
    zoznamKnih.pridajKnihu(Kniha("1984", "George Orwell", 1949, Date(2003, 9, 7)))
    zoznamKnih.pridajKnihu(Kniha("Hobit", "J. R. R. Tolkien", 1937))
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun VypisanieKnih(zoznam: ZoznamKnih) {
    Row (horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (kniha in zoznam.iterator()) {
                Text(kniha.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VAMZAplikaciaTheme {
        VytvorZoznam()
        VypisanieKnih(zoznam = zoznamKnih)
    }
}