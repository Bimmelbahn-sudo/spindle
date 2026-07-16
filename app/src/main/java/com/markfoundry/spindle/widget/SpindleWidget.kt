package com.markfoundry.spindle.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.widget.RemoteViews
import com.markfoundry.spindle.R
import com.markfoundry.spindle.media.SpindleNotificationListener

/** Builds the widget's RemoteViews from the active media session and pushes updates. */
object SpindleWidget {

    const val ACTION_PREV = "com.markfoundry.spindle.widget.PREV"
    const val ACTION_PLAY_PAUSE = "com.markfoundry.spindle.widget.PLAY_PAUSE"
    const val ACTION_NEXT = "com.markfoundry.spindle.widget.NEXT"

    fun updateAll(context: Context) {
        val manager = AppWidgetManager.getInstance(context) ?: return
        val ids = manager.getAppWidgetIds(ComponentName(context, SpindleWidgetProvider::class.java))
        if (ids.isEmpty()) return
        val views = buildViews(context)
        ids.forEach { manager.updateAppWidget(it, views) }
    }

    fun buildViews(context: Context): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.widget_spindle)
        val controller = currentController(context)
        val md = controller?.metadata
        val playing = controller?.playbackState?.state == PlaybackState.STATE_PLAYING

        if (md != null) {
            views.setTextViewText(R.id.widget_title, md.getString(MediaMetadata.METADATA_KEY_TITLE) ?: "Unknown")
            views.setTextViewText(R.id.widget_artist, md.getString(MediaMetadata.METADATA_KEY_ARTIST) ?: "")
            val art = md.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
                ?: md.getBitmap(MediaMetadata.METADATA_KEY_ART)
                ?: md.getBitmap(MediaMetadata.METADATA_KEY_DISPLAY_ICON)
            if (art != null) views.setImageViewBitmap(R.id.widget_art, circularBitmap(art))
        } else {
            views.setTextViewText(R.id.widget_title, "Nothing playing")
            views.setTextViewText(R.id.widget_artist, "Open a music app")
        }
        views.setImageViewResource(
            R.id.widget_playpause,
            if (playing) R.drawable.ic_widget_pause else R.drawable.ic_widget_play,
        )

        views.setOnClickPendingIntent(R.id.widget_prev, controlIntent(context, ACTION_PREV))
        views.setOnClickPendingIntent(R.id.widget_playpause, controlIntent(context, ACTION_PLAY_PAUSE))
        views.setOnClickPendingIntent(R.id.widget_next, controlIntent(context, ACTION_NEXT))
        return views
    }

    fun currentController(context: Context): MediaController? = try {
        val msm = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        val component = ComponentName(context, SpindleNotificationListener::class.java)
        msm.getActiveSessions(component).firstOrNull()
    } catch (e: SecurityException) {
        null
    }

    private fun controlIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, SpindleWidgetProvider::class.java).setAction(action)
        return PendingIntent.getBroadcast(
            context,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun circularBitmap(src: Bitmap): Bitmap {
        val size = minOf(src.width, src.height).coerceAtLeast(1)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val shader = BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        shader.setLocalMatrix(Matrix().apply { setTranslate((size - src.width) / 2f, (size - src.height) / 2f) })
        paint.shader = shader
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        return output
    }
}
