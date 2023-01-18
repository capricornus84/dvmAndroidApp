package com.dvm.dvmproject8.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.view.notifications.NotificationConstants
import com.dvm.dvmproject8.view.notifications.NotificationHelper

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationConstants.FILM_KEY) as Film

        NotificationHelper.createNotification(context!!, film)
    }
}