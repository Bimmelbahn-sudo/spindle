package com.markfoundry.spindle.media

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings

/** Helpers for the one permission Spindle needs: Notification access. */
object NotificationAccess {

    fun isGranted(context: Context): Boolean {
        val component = ComponentName(context, SpindleNotificationListener::class.java)
        val enabled = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners",
        ) ?: return false
        return enabled.split(":").any { ComponentName.unflattenFromString(it) == component }
    }

    fun settingsIntent(): Intent =
        Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}
