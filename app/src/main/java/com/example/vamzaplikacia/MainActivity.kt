package com.example.vamzaplikacia

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
import com.example.vamzaplikacia.knihy.Kniha
import com.example.vamzaplikacia.knihy.Vstup
import com.example.vamzaplikacia.knihy.ZoznamKnih
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme
import java.util.Date

val zoznamKnih = ZoznamKnih()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAMZAplikaciaTheme {
                VytvorZoznam()
                VypisanieKnih(zoznam = zoznamKnih)
            }
        }
    }
}

fun VytvorZoznam() {
    zoznamKnih.pridajKnihu(Kniha(
        "Veľký Gatsby",
        "F. Scott Fitzgerald",
        1925,
    ))
    zoznamKnih.pridajKnihu(Kniha(
        "Alchymista",
        "Paulo Coelho",
        1988,
        Date(2000, 2, 2),
    ))
    zoznamKnih.get(1)?.favorit = true;
    zoznamKnih.pridajKnihu(Kniha(
        "1984",
        "George Orwell",
        1949,
        Date(2003, 9, 7)
    ))
    zoznamKnih.pridajKnihu(Kniha(
        "Hobit",
        "J. R. R. Tolkien",
        1937,
        obrazok = R.drawable.hobit
    ))
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
                ListItem(
                    headlineContent = { Text(kniha.nazov) },
                    supportingContent = { Text(kniha.autor + ", " + kniha.rokVydania) },
                    trailingContent = {
                        if (kniha.favorit) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                            )
                        }
                        else {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "Localized description",
                            )
                        }
                    },
                    leadingContent = {
                        Image(
                            painter = painterResource(kniha.obrazok),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(40.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                )
            }
        }
    }
}


@Preview(name = "Light Mode")
/*@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)*/
@Composable
fun GreetingPreview() {
    VAMZAplikaciaTheme {
        VytvorZoznam()
        VypisanieKnih(zoznam = zoznamKnih)
    }
}