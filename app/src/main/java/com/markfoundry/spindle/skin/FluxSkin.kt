package com.markfoundry.spindle.skin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markfoundry.spindle.player.NowPlaying
import com.markfoundry.spindle.player.PlayerControls
import com.markfoundry.spindle.ui.NextIcon
import com.markfoundry.spindle.ui.PauseIcon
import com.markfoundry.spindle.ui.PlayIcon
import com.markfoundry.spindle.ui.PrevIcon

private val DarkOverlay = Color(0xCC000000)
private val PanelDark = Color(0xE6000000)
private val White = Color(0xFFF5F5F5)
private val DimWhite = Color(0xFFCCCCCC)
private val ButtonDark = Color(0x99000000)

object FluxSkin : PlayerSkin {

    override val id = "flux"
    override val displayName = "Flux"

    @Composable
    override fun Render(
        state: NowPlaying,
        controls: PlayerControls
    ) {
        FluxScreen(state, controls)
    }
}

@Composable
private fun FluxScreen(
    state: NowPlaying,
    controls: PlayerControls
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // ------------------------------------------------------------
        // ALBUMCOVER ALS HINTERGRUND
        // ------------------------------------------------------------

        val art = state.artwork

        if (art != null) {
            Image(
                bitmap = art,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF111111))
            )
        }

        // ------------------------------------------------------------
        // DUNKLER OVERLAY
        // ------------------------------------------------------------

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.25f),
                            Color.Black.copy(alpha = 0.35f),
                            Color.Black.copy(alpha = 0.75f),
                            Color.Black.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        // ------------------------------------------------------------
        // INHALT
        // ------------------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 45.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Oben
            Text(
                text = "NOW PLAYING",
                color = White.copy(alpha = 0.75f),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 4.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            // --------------------------------------------------------
            // TITEL / INTERPRET
            // --------------------------------------------------------

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = state.title,
                    color = White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 34.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )

                val sub = listOfNotNull(
                    state.artist,
                    state.album
                ).joinToString("  ·  ")

                if (sub.isNotBlank()) {
                    Text(
                        text = sub,
                        color = DimWhite,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // --------------------------------------------------------
            // FORTSCHRITTSBALKEN
            // --------------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            state.progress.coerceIn(0f, 1f)
                        )
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(White)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // --------------------------------------------------------
            // GROSSE MUSIK-TASTEN
            // --------------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ZURÜCK
                BigControlButton(
                    onClick = controls::previous,
                    diameter = 105.dp
                ) {
                    PrevIcon(White)
                }

                Spacer(modifier = Modifier.size(45.dp))

                // PLAY / PAUSE
                BigControlButton(
                    onClick = controls::playPause,
                    diameter = 145.dp,
                    mainButton = true
                ) {
                    if (state.isPlaying) {
                        PauseIcon(Color.Black)
                    } else {
                        PlayIcon(Color.Black)
                    }
                }

                Spacer(modifier = Modifier.size(45.dp))

                // WEITER
                BigControlButton(
                    onClick = controls::next,
                    diameter = 105.dp
                ) {
                    NextIcon(White)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
private fun BigControlButton(
    onClick: () -> Unit,
    diameter: Dp,
    mainButton: Boolean = false,
    content: @Composable () -> Unit
) {

    val background = if (mainButton) {
        Color.White
    } else {
        ButtonDark
    }

    Box(
        modifier = Modifier
            .size(diameter)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
