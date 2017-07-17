package de.Maxr1998.android.lcd_off

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class StartupActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, RecoverService::class.java as Class<*>))
        finish()
    }
}