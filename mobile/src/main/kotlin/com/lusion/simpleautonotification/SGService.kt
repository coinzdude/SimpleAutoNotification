package com.lusion.simpleautonotification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.car.app.CarAppService
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.notification.CarNotificationManager
import androidx.car.app.validation.HostValidator
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

/**
 * Entry point for the hello world app.
 *
 *
 * [CarAppService] is the main interface between the app and the car host. For more
 * details, see the [Android for
 * Cars Library developer guide](https://developer.android.com/training/cars/navigation).
 */
//@Suppress("DEPRECATION")
class SGService : CarAppService() {
    override fun onCreateSession(): Session {
        Log.i(TAG, "onCreateSession")
        initNotifications(this)

        return object : Session() {
            override fun onCreateScreen(intent: Intent): Screen {
                return SGScreen(carContext)
            }
        }
    }


    @SuppressLint("PrivateResource")
    override fun createHostValidator(): HostValidator {
        Log.i(TAG, "createHostValidator")
        return if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
        } else {
            HostValidator.Builder(applicationContext)
                .addAllowedHosts(androidx.car.app.R.array.hosts_allowlist_sample)
                .build()
        }
    }

    companion object {
        private val TAG = (SGService::class.qualifiedName)!!
        private const val GARAGE_CLOSE = "GARAGE_CLOSE"
        private const val GARAGE_OPEN = "GARAGE_OPEN"
        private fun initNotifications(context: Context) {
            val navChannel = NotificationChannelCompat.Builder(
                context.getString(R.string.app_name),
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName(context.getString(R.string.app_name)).build()
            CarNotificationManager.from(context).createNotificationChannel(navChannel)
        }
    }
}