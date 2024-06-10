package com.example.vamzaplikacia

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Calendar

/**
 * Trieda ktorá má na starosti zobrazenie a zavolanie notifikácie.
 */
class Notifikacia {

    fun zavolanie(context: Context, intent: Intent) {
        vytvorKanalNotifikacie(context)
        zobrazNotifikaciu(context)
    }

    /**
     * Kanál notifikácie.
     *
     * @param context
     */
    private fun vytvorKanalNotifikacie(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notifikacia_po_zavreti)
            val descriptionText = context.getString(R.string.kanal_pre_notifikaciu_apky)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(context.getString(R.string.denna_notifikacia_kanal), name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Zobrazí notifikáciu, ak sa dá.
     *
     * @param context
     */
    private fun zobrazNotifikaciu(context: Context) {
        try {
            val notifikaciaText = nacitajTextNotifikacie(context)

            val builder = NotificationCompat.Builder(context, context.getString(R.string.denna_notifikacia_kanal))
                .setSmallIcon(R.drawable.notifikacia)
                .setContentTitle(context.getString(R.string.knizny_vyrok_dna))
                .setContentText(notifikaciaText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(notifikaciaText))


            with(NotificationManagerCompat.from(context)) {
                notify(1, builder.build())
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    /**
     * Načíta správny výrok zo súboru podľa dňa v roku (zatiaľ len 100 výrokov)
     *
     * @param context
     */
    fun nacitajTextNotifikacie(context: Context): String {
        val inputStream = context.resources.openRawResource(R.raw.vyroky)
        val text = inputStream.bufferedReader().use {it.readText()}

        val vyroky = text.split("\n")

        val kalendar = Calendar.getInstance()
        val den = kalendar.get(Calendar.DAY_OF_YEAR)%100

        return vyroky[den]
    }
}