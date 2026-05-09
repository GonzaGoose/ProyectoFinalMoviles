package com.example.projectofinalmoviles.componentes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.projectofinalmoviles.MainActivity
import com.example.projectofinalmoviles.R

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "medicamentos_channel"
    private val CHANNEL_NAME = "Recordatorio de Medicamentos"

    fun mostrarNotificacion(medicamento: Medicamento) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            medicamento.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_alarm_24)
            .setContentTitle("¡Hora de tomar medicamento!")
            .setContentText("${medicamento.nombre} - Te quedan ${medicamento.dosisRestantes} dosis")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Es hora de tomar ${medicamento.nombre}. Próxima dosis en ${medicamento.horas} horas."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(medicamento.id.hashCode(), notification)
    }
}