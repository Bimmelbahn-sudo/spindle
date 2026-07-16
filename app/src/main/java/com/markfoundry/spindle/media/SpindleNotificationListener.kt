package com.markfoundry.spindle.media

import android.service.notification.NotificationListenerService

/**
 * A no-op notification listener. Its only job is to *exist* and be enabled by the
 * user (Settings -> Notification access). Enabling it is what authorizes
 * [android.media.session.MediaSessionManager.getActiveSessions]. We don't read
 * notifications here — playback goes through MediaController.
 */
class SpindleNotificationListener : NotificationListenerService()
