package com.markfoundry.spindle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.markfoundry.spindle.player.NoopControls
import com.markfoundry.spindle.player.PreviewNowPlaying
import com.markfoundry.spindle.skin.defaultSkin
import com.markfoundry.spindle.ui.theme.SpindleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpindleApp()
        }
    }
}

@Composable
fun SpindleApp() {
    SpindleTheme {
        // Phase 1: render the default skin with sample data.
        // Phase 2 replaces PreviewNowPlaying / NoopControls with live media-session state.
        defaultSkin().Render(state = PreviewNowPlaying, controls = NoopControls)
    }
}
