package com.markfoundry.spindle.skin

import androidx.compose.runtime.Composable
import com.markfoundry.spindle.player.NowPlaying
import com.markfoundry.spindle.player.PlayerControls

/**
 * A swappable visual identity for the player. Every skin renders the same
 * [NowPlaying] state and drives the same [PlayerControls], so adding a skin
 * never touches the media/core layer.
 */
interface PlayerSkin {
    val id: String
    val displayName: String

    @Composable
    fun Render(state: NowPlaying, controls: PlayerControls)
}
