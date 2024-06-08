import android.content.res.Resources
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getString
import com.example.vamzaplikacia.R

@Composable
fun VyberObrazkuButton(onImagePicked: (Uri) -> Unit) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImagePicked(it) }
    }

    val context = LocalContext.current

    Button(onClick = {
        launcher.launch(getString(context, R.string.image_moznost))
    }) {
        Text(stringResource(R.string.vyber_obrazok))
    }


}

