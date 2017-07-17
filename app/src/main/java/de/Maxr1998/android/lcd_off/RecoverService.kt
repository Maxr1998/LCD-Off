package de.Maxr1998.android.lcd_off

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import eu.chainfire.libsuperuser.Shell


class RecoverService : Service() {

    @Suppress("PrivatePropertyName")
    private val PATH = "/sys/class/leds/lcd-backlight/brightness"

    private var prevBrightness = 20

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            setScreenBrightness(prevBrightness)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        prevBrightness = getScreenBrightness()
        setScreenBrightness(0)
        registerReceiver(receiver, IntentFilter().apply { addAction(Intent.ACTION_SCREEN_OFF) })
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setScreenBrightness(brightness: Int) {
        Shell.SU.run("echo $brightness > $PATH")
    }

    private fun getScreenBrightness() = Shell.SU.run("cat $PATH")[0].toInt()
}