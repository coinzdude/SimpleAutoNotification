package com.lusion.simpleautonotification

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.car.app.notification.CarAppExtender
import androidx.car.app.notification.CarPendingIntent
import androidx.core.app.NotificationCompat

object DeviceFunction {
    private var notificationTitle: String? = null
    fun getNotificationBuilder(
        context: Context,
        pendingNotificationIntent: PendingIntent?,
        notificationImageID: Int?

    ): NotificationCompat.Builder {
        // simple hack to force android auto to update icon on new notifications
        notificationTitle = "Smarter Garage"
        return NotificationCompat.Builder(context, context.getString(R.string.garage_notification))
            .setContentTitle("Title")
            .setContentText("This is your Smarter Garage")
            .setSmallIcon(notificationImageID!!)
            .setContentIntent(pendingNotificationIntent)
            .setSilent(true)
            .setChannelId(context.getString(R.string.garage_notification))
            .setAutoCancel(false)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .extend(
                CarAppExtender.Builder()
                    .setContentTitle("Title")
                    .setContentIntent(
                        CarPendingIntent.getCarApp(
                            context.applicationContext, 0,
                            Intent(Intent.ACTION_VIEW).setComponent(
                                ComponentName(
                                    context.applicationContext,
                                    SGService::class.java
                                )
                            ), 0
                        )
                    )
                    .setChannelId(context.getString(R.string.garage_notification))
                    .build()
            )
    }
}