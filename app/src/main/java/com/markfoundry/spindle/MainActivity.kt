package com.markfoundry.spindle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.markfoundry.spindle.media.NotificationAccess
import com.markfoundry.spindle.skin.defaultSkin
import com.markfoundry.spindle.ui.NothingPlayingScreen
import com.markfoundry.spindle.ui.OnboardingScreen
import com.markfoundry.spindle.ui.theme.SpindleTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { SpindleApp(viewModel) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}

@Composable
private fun SpindleApp(viewModel: MainViewModel) {
    SpindleTheme {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        var granted by remember { mutableStateOf(viewModel.isAccessGranted()) }

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    granted = viewModel.isAccessGranted()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        if (!granted) {
            OnboardingScreen(
                onGrantClick = { context.startActivity(NotificationAccess.settingsIntent()) },
            )
        } else {
            val nowPlaying by viewModel.state.collectAsStateWithLifecycle()
            val np = nowPlaying
            if (np == null) {
                NothingPlayingScreen()
            } else {
                defaultSkin().Render(state = np, controls = viewModel.controls)
            }
        }
    }
}
