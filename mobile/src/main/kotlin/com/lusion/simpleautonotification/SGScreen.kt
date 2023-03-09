package com.lusion.simpleautonotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.TimerTask

/**
 * Smarter Garage default Auto launch screen
 *
 *
 * See [SGService] for the app's entry point to the car host.
 */

class SGScreen(carContext: CarContext) : Screen(carContext), DefaultLifecycleObserver {
    override fun onGetTemplate(): Template {
        val itemList = ItemList.Builder()
        val updateScreen: TimerTask = object : TimerTask() {
            override fun run() {
                invalidate()
            }
        }
        itemList.addItem(
                    Row.Builder()
                        .setTitle("Row")
                        .build())

        // NAVIGATION // .push(GarageListNavigationScreen.create(this.getCarContext(), null));
        return ListTemplate.Builder()
            .setSingleList(itemList.build())
            .setTitle("App Name")
            .setHeaderAction(Action.APP_ICON)
            .build()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.i(TAG, "onDestroy")
        stopMonitoring()
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.i(TAG, "onPause")
        stopMonitoring()
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.i(TAG, "onStop")
        stopMonitoring()
    }

    private fun stopMonitoring() {
//        CarNotificationManager.from(getCarContext()).cancelAll();
        unregisterBroadcastReceiver()
    }

    private fun unregisterBroadcastReceiver() {
        Log.i(TAG, "unregisterBroadcastReceiver")
        carContext.unregisterReceiver(mBroadcastReceiver)
    }

    private val mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            invalidate()
        }
    }

    //        Intent intent = new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:4444444444"));
    //        return CarPendingIntent.getCarApp(getCarContext(), intent.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE);
    //    }
    // Since I can't figure out how to add this to the manifest and make it open to other apps, it's an 'internal' receiver which gets data sent from another standard receiver, "AutoUpdateRequestReceiver"
    companion object {
        //LusionUtility.GarageStatus _garageStatus = LusionUtility.GarageStatus.UNKNOWN;
        private const val TAG = "SGScreen"
        private var lastRunTime: Long = 0
    }
}