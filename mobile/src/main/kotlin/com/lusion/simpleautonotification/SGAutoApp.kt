package com.lusion.simpleautonotification

import android.app.Application
import android.content.Context
//import android.os.StrictMode

// For use in strict mode, to enable, update auto manifest with
// android:name=".SGAutoApp"
class SGAutoApp : Application() {
    init {
//        if (BuildConfig.DEBUG) StrictMode.enableDefaults()
    }
    companion object {
        fun getAppContext(): Context {
            return this.getAppContext()
        }
    }
}