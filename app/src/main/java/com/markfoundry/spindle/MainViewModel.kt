package com.markfoundry.spindle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.markfoundry.spindle.media.MediaSessionRepository
import com.markfoundry.spindle.media.NotificationAccess
import com.markfoundry.spindle.player.PlayerControls
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = MediaSessionRepository(app)

    val state = repository.state
    val controls: PlayerControls = repository

    private var running = false
    private var ticker: Job? = null

    fun isAccessGranted(): Boolean = NotificationAccess.isGranted(getApplication())

    /** Bind to the media session and start ticking progress. Safe to call repeatedly. */
    fun onResume() {
        if (running || !isAccessGranted()) return
        running = true
        repository.start()
        ticker = viewModelScope.launch {
            while (isActive) {
                repository.tick()
                delay(1000)
            }
        }
    }

    fun onPause() {
        running = false
        ticker?.cancel()
        ticker = null
        repository.stop()
    }

    override fun onCleared() {
        ticker?.cancel()
        repository.stop()
    }
}
