package com.lusion.simpleautonotification

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object LusionUtility {
    @JvmStatic
    @SuppressLint("ObsoleteSdkInt")
    fun triggerNotification(
        context: Context,
    ) {

        val intent = Intent(context, MainActivity::class.java)
        val pendingNotificationIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notificationImageID = R.drawable.geofence_in

        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.garage_notification),
                context.packageName,
                NotificationManager.IMPORTANCE_MIN
            )
            channel.setShowBadge(true)
            notificationManager.createNotificationChannel(channel)
        }

        // Get an 'importance' from the preference which matches NotificationManagerCompat values by name
        val builder = DeviceFunction.getNotificationBuilder(
            context,
            pendingNotificationIntent,
            notificationImageID
        )
        notificationManager.notify(
            context.resources.getInteger(R.integer.garage_notification_id),
            builder.build()
        )

        }
        //        // if we get a notification call, and we have not checked the garage status in 5 minutes, go ahead and run a poll cycle
//        if (System.currentTimeMillis() - _lastGarageStatusCheckTime > 300000) {
//            Log.i(TAG, "triggerNotification calling monitorGarageStatus");
//            monitorGarageStatus(context, false, _maxGaragePollingTime, true, "triggerNotification based on long delay since last check");
//        }
    }

    // method to request for permissions
    private fun requestPermissions(activity: Activity) {
//        gSettingsActivity = activity
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        )
        // Provide an additional rationale to the user. This would happen if the user denied the request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
        } else {
            // Request permission. It's possible this can be auto answered if device policy sets the permission in a given state or the user denied the permission previously and checked "Never ask again".
        }
    }

    internal fun hasPermission(context: Context?, permissionName: String?): Boolean {
        return ActivityCompat.checkSelfPermission(
            context!!,
            permissionName!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    internal fun isGranted(
        permissions: Array<String>,
        grantResults: IntArray,
        permissionName: String
    ): Boolean {
        val permGrantIdx = listOf(*permissions).indexOf(permissionName)
        return permGrantIdx != -1 && grantResults[permGrantIdx] != -1 // when both are not -1, permission was granted, return true
    }

    fun requestPermission(
        activity: AppCompatActivity?,
        permissionName: String,
        rationale: String?,
        requestCode: Int
    ) {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity!!,
            permissionName
        )
        // Provide an additional rationale to the user. This would happen if the user denied the request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
//            if (rationale == null)
//                rationale = activity.getString(R.string.fine_location_permission_rationale);
//            View contentView = ((AppCompatActivity) activity).findViewById(android.R.id.content);
            val builder = AlertDialog.Builder(
                activity
            )
            builder.setTitle("Permission needed")
                .setMessage(rationale)
                .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                    ActivityCompat.requestPermissions(
                        activity, arrayOf(permissionName), requestCode
                    )
                }
                .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            builder.create().show()

//            Snackbar.make(contentView, rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            _blockingSnackbar = false;
//                            ActivityCompat.requestPermissions(activity, new String[]{permissionName}, requestCode);
//                        }
//                    })
//                    .show();
            //showSnackbar(activity, rationale);
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(permissionName), requestCode)
        }
    }

