package com.markfoundry.spindle.skin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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

private val Void = Color(0xFF06070D)
private val VoidTop = Color(0xFF10132A)
private val Panel = Color(0xFF0C1020)
private val Cyan = Color(0xFF3FE6F2)
private val Violet = Color(0xFF8B6BFF)
private val Magenta = Color(0xFFFF4D8D)
private val FluxText = Color(0xFFEAF2FF)
private val FluxDim = Color(0xFF8794B3)

object FluxSkin : PlayerSkin {
    override val id = "flux"
    override val displayName = "Flux"

    @Composable
    override fun Render(state: NowPlaying, controls: PlayerControls) {
        FluxScreen(state, controls)
    }
}

@Composable
private fun FluxScreen(state: NowPlaying, controls: PlayerControls) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(VoidTop, Void)))
            .systemBarsPadding(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("SPINDLE", color = Cyan, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, letterSpacing = 3.sp)
            Text("NOW PLAYING", color = FluxDim, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 3.sp)
        }

        Box(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.82f).aspectRatio(1f), contentAlignment = Alignment.Center) {
                HoloRing(isPlaying = state.isPlaying, modifier = Modifier.fillMaxSize())
                Box(
                    modifier = Modifier.fillMaxSize(0.6f).clip(CircleShape).background(Panel),
                    contentAlignment = Alignment.Center,
                ) {
                    val art = state.artwork
                    if (art != null) {
                        Image(
                            bitmap = art,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Box(Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Violet, Cyan))))
                    }
                }
            }
        }

        FluxPanel(state = state, controls = controls)
    }
}

@Composable
private fun HoloRing(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val rotation = remember { Animatable(0f) }
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                rotation.animateTo(
                    targetValue = rotation.value + 360f,
                    animationSpec = tween(durationMillis = 6000, easing = LinearEasing),
                )
            }
        }
    }
    Canvas(modifier = modifier.rotate(rotation.value)) {
        val stroke = size.minDimension * 0.07f
        drawCircle(
            brush = Brush.sweepGradient(listOf(Cyan, Violet, Magenta, Cyan), center = center),
            radius = size.minDimension / 2f - stroke / 2f,
            center = center,
            style = Stroke(width = stroke),
        )
    }
}

@Composable
private fun FluxPanel(state: NowPlaying, controls: PlayerControls) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(Brush.verticalGradient(listOf(Panel.copy(alpha = 0.92f), Void)))
            .padding(horizontal = 24.dp, vertical = 22.dp),
    ) {
        Text(state.title, color = FluxText, fontWeight = FontWeight.Bold, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        val sub = listOfNotNull(state.artist, state.album).joinToString("  ·  ")
        Text(sub, color = FluxDim, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 3.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 18.dp).height(4.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.12f)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(state.progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(Brush.horizontalGradient(listOf(Cyan, Violet))),
            )
        }

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FluxButton(onClick = controls::previous, diameter = 50.dp, glow = false) { PrevIcon(Cyan) }
            Spacer(Modifier.width(18.dp))
            FluxButton(onClick = controls::playPause, diameter = 66.dp, glow = true) {
                if (state.isPlaying) PauseIcon(Void) else PlayIcon(Void)
            }
            Spacer(Modifier.width(18.dp))
            FluxButton(onClick = controls::next, diameter = 50.dp, glow = false) { NextIcon(Cyan) }
        }
    }
}

@Composable
private fun FluxButton(
    onClick: () -> Unit,
    diameter: Dp,
    glow: Boolean,
    content: @Composable () -> Unit,
) {
    val fill = if (glow) {
        Brush.linearGradient(listOf(Cyan, Violet))
    } else {
        Brush.linearGradient(listOf(Color.White.copy(alpha = 0.10f), Color.White.copy(alpha = 0.04f)))
    }
    Box(
        modifier = Modifier.size(diameter).clip(CircleShape).background(fill).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
