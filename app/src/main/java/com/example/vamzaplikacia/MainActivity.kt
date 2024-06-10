package com.example.vamzaplikacia

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.vamzaplikacia.data.AppDataContainer
import com.example.vamzaplikacia.logika.knihy.Kniznica
import com.example.vamzaplikacia.ui.theme.VAMZAplikaciaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = AppDataContainer(this)
        val kniznica = Kniznica(applicationContext)

        createNotificationChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            VAMZAplikaciaTheme {
                SpustenieProgramu(container, kniznica)
            }
        }
    }


    override fun onDestroy() {
        val receiver = Notifikacia()
        val context = this
        val intent = Intent("com.your.package.SHOW_NOTIFICATION")
        receiver.zavolanie(context)
        super.onDestroy()
    }

    /*override fun onStop() {
        if (prveSpustenie) {
            prveSpustenie = false
            val receiver = Notifikacia()
            val context = this
            val intent = Intent("com.your.package.SHOW_NOTIFICATION")
            receiver.zavolanie(context, intent)
        }
        super.onStop()
    }*/

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            createNotificationChannel(this)
        }
    }

    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.notifikacia_po_zavreti)
        val descriptionText = context.getString(R.string.kanal_pre_notifikaciu_apky)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(context.getString(R.string.denna_notifikacia_kanal), name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}
