package com.lusion.simpleautonotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

// This AutoUpdateRequestReceiver runs in auto app and acts as a public accessible proxy to receive intents from our mobile app which then must be relayed to the SGScreen by new intent.
class AutoUpdateRequestReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            // This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast, like from the auto app
            Log.i(TAG, "AutoUpdateRequestReceiver received intent ${intent.action}")
            Log.i(
                TAG,
                "AutoUpdateRequestReceiver rebroadcasting internally with SGService"
            )
            val relayIntent = Intent("com.lusion.simpleautonotification.SGService")
            relayIntent.putExtras(intent)

            //relayIntent.setAction("com.lusion.sgauto.SGScreen");
            Log.i(TAG, "context.sendBroadcast(relayIntent)")
            context.sendBroadcast(relayIntent)
        } catch (e: Exception) {
            Log.w(TAG, e.message!!)
        }
    }

    companion object {
        private const val TAG = "AutoUpdateRequestReceiver"
    }
}