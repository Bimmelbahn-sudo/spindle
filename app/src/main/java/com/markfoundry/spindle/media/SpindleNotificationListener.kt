package com.markfoundry.spindle.media

import android.content.ComponentName
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import com.markfoundry.spindle.widget.SpindleWidget

/**
 * Enabling this listener (Settings -> Notification access) is what authorizes
 * MediaSessionManager access for the whole app. While connected, it also tracks
 * the active session and keeps any home-screen widgets fresh.
 */
class SpindleNotificationListener : NotificationListenerService() {

    private val sessionManager by lazy {
        getSystemService(MEDIA_SESSION_SERVICE) as MediaSessionManager
    }
    private val component by lazy { ComponentName(this, SpindleNotificationListener::class.java) }

    private var tracked: MediaController? = null

    private val controllerCallback = object : MediaController.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadata?) = SpindleWidget.updateAll(applicationContext)
        override fun onPlaybackStateChanged(state: PlaybackState?) = SpindleWidget.updateAll(applicationContext)
        override fun onSessionDestroyed() = retrack()
    }

    private val sessionsListener = MediaSessionManager.OnActiveSessionsChangedListener { retrack() }

    override fun onListenerConnected() {
        try {
            sessionManager.addOnActiveSessionsChangedListener(sessionsListener, component)
            retrack()
        } catch (e: SecurityException) {
            // Access not granted yet.
        }
    }

    override fun onListenerDisconnected() {
        runCatching { sessionManager.removeOnActiveSessionsChangedListener(sessionsListener) }
        tracked?.unregisterCallback(controllerCallback)
        tracked = null
    }

    private fun retrack() {
        val next = try {
            sessionManager.getActiveSessions(component).firstOrNull()
        } catch (e: SecurityException) {
            null
        }
        if (next?.sessionToken != tracked?.sessionToken) {
            tracked?.unregisterCallback(controllerCallback)
            tracked = next
            tracked?.registerCallback(controllerCallback)
        }
        SpindleWidget.updateAll(applicationContext)
    }
}
