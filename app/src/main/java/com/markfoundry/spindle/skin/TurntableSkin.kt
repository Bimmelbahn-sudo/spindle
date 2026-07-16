package com.markfoundry.spindle.skin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markfoundry.spindle.player.NoopControls
import com.markfoundry.spindle.player.NowPlaying
import com.markfoundry.spindle.player.PlayerControls
import com.markfoundry.spindle.player.PreviewNowPlaying

// Flat-retro turntable palette.
private val Coral = Color(0xFFE8645A)
private val Navy = Color(0xFF2B356A)
private val NavyLine = Color(0xFF3C4890)
private val Cream = Color(0xFFF2E7D0)
private val CreamDark = Color(0xFFE3D3B2)
private val Red = Color(0xFFD94B3F)
private val Blue = Color(0xFF33429C)
private val Vinyl = Color(0xFF17130F)
private val Groove = Color(0xFF241C17)
private val Silver = Color(0xFFD9D5CC)

object TurntableSkin : PlayerSkin {
    override val id = "turntable"
    override val displayName = "Turntable"

    @Composable
    override fun Render(state: NowPlaying, controls: PlayerControls) {
        TurntableScreen(state, controls)
    }
}

@Composable
private fun TurntableScreen(state: NowPlaying, controls: PlayerControls) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Coral)
            .systemBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("SPINDLE", color = Navy, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, letterSpacing = 2.sp)
            Text("NOW PLAYING", color = Navy, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 2.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.9f).aspectRatio(1f)) {
                Record(isPlaying = state.isPlaying, modifier = Modifier.fillMaxSize())
                Tonearm(isPlaying = state.isPlaying, modifier = Modifier.fillMaxSize())
            }
        }

        DeckPanel(state = state, controls = controls)
    }
}

@Composable
private fun Record(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val rotation = remember { Animatable(0f) }
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                rotation.animateTo(
                    targetValue = rotation.value + 360f,
                    animationSpec = tween(durationMillis = 8000, easing = LinearEasing),
                )
            }
        }
    }
    Canvas(modifier = modifier.rotate(rotation.value)) {
        val c = center
        val outer = size.minDimension / 2f
        val labelR = outer * 0.42f
        drawCircle(color = Vinyl, radius = outer, center = c)
        val stroke = Stroke(width = 1.2.dp.toPx())
        var r = outer * 0.96f
        while (r > labelR) {
            drawCircle(color = Groove, radius = r, center = c, style = stroke)
            r -= 3.5.dp.toPx()
        }
        drawCircle(color = Cream.copy(alpha = 0.85f), radius = outer * 0.9f, center = c, style = Stroke(width = 1.5.dp.toPx()))
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF2F5F96), Color(0xFFD7823F)),
                start = Offset(c.x - labelR, c.y - labelR),
                end = Offset(c.x + labelR, c.y + labelR),
            ),
            radius = labelR,
            center = c,
        )
        drawCircle(color = Cream, radius = labelR, center = c, style = Stroke(width = 3.dp.toPx()))
        drawCircle(color = Coral, radius = outer * 0.05f, center = c)
    }
}

@Composable
private fun Tonearm(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val lift by animateFloatAsState(
        targetValue = if (isPlaying) 0f else -20f,
        animationSpec = tween(500),
        label = "tonearm-lift",
    )
    Canvas(
        modifier = modifier.graphicsLayer {
            rotationZ = lift
            transformOrigin = TransformOrigin(0.82f, 0.14f)
        },
    ) {
        val pivot = Offset(size.width * 0.82f, size.height * 0.14f)
        val head = Offset(size.width * 0.52f, size.height * 0.46f)
        drawLine(color = Silver, start = pivot, end = head, strokeWidth = 6.dp.toPx(), cap = StrokeCap.Round)
        drawCircle(color = Blue, radius = 16.dp.toPx(), center = pivot)
        drawCircle(color = Cream, radius = 7.dp.toPx(), center = pivot)
        drawCircle(color = Blue, radius = 9.dp.toPx(), center = head)
    }
}

@Composable
private fun DeckPanel(state: NowPlaying, controls: PlayerControls) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
            .background(Navy)
            .padding(horizontal = 22.dp, vertical = 20.dp),
    ) {
        Text(state.title, color = Cream, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        val sub = listOfNotNull(state.artist, state.album).joinToString("  •  ")
        Text(sub, color = CreamDark, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 2.dp))

        ProgressBar(progress = state.progress, modifier = Modifier.padding(top = 16.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(formatTime(state.positionMs), color = CreamDark, fontSize = 10.sp)
            Text(formatTime(state.durationMs), color = CreamDark, fontSize = 10.sp)
        }

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ControlButton(onClick = controls::previous, diameter = 48.dp, background = Cream) { PrevIcon(Vinyl) }
            Spacer(Modifier.width(16.dp))
            ControlButton(onClick = controls::playPause, diameter = 64.dp, background = Red) {
                if (state.isPlaying) PauseIcon(Cream) else PlayIcon(Cream)
            }
            Spacer(Modifier.width(16.dp))
            ControlButton(onClick = controls::next, diameter = 48.dp, background = Cream) { NextIcon(Vinyl) }
        }
    }
}

@Composable
private fun ProgressBar(progress: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(CircleShape)
            .background(NavyLine),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(CircleShape)
                .background(Coral),
        )
    }
}

@Composable
private fun ControlButton(
    onClick: () -> Unit,
    diameter: Dp,
    background: Color,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(diameter)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun PlayIcon(tint: Color) {
    Canvas(Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.24f, h * 0.16f)
            lineTo(w * 0.84f, h * 0.5f)
            lineTo(w * 0.24f, h * 0.84f)
            close()
        }
        drawPath(path, color = tint)
    }
}

@Composable
private fun PauseIcon(tint: Color) {
    Canvas(Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val barW = w * 0.22f
        val radius = CornerRadius(2.dp.toPx())
        drawRoundRect(color = tint, topLeft = Offset(w * 0.2f, h * 0.16f), size = Size(barW, h * 0.68f), cornerRadius = radius)
        drawRoundRect(color = tint, topLeft = Offset(w * 0.58f, h * 0.16f), size = Size(barW, h * 0.68f), cornerRadius = radius)
    }
}

@Composable
private fun NextIcon(tint: Color) {
    Canvas(Modifier.size(22.dp)) {
        val w = size.width
        val h = size.height
        drawPath(triangle(w * 0.12f, w * 0.5f, h), tint)
        drawPath(triangle(w * 0.46f, w * 0.84f, h), tint)
        drawRoundRect(color = tint, topLeft = Offset(w * 0.85f, h * 0.22f), size = Size(w * 0.11f, h * 0.56f), cornerRadius = CornerRadius(1.5.dp.toPx()))
    }
}

@Composable
private fun PrevIcon(tint: Color) {
    Canvas(Modifier.size(22.dp)) {
        val w = size.width
        val h = size.height
        drawRoundRect(color = tint, topLeft = Offset(w * 0.04f, h * 0.22f), size = Size(w * 0.11f, h * 0.56f), cornerRadius = CornerRadius(1.5.dp.toPx()))
        drawPath(triangleLeft(w * 0.88f, w * 0.5f, h), tint)
        drawPath(triangleLeft(w * 0.54f, w * 0.16f, h), tint)
    }
}

private fun triangle(leftX: Float, tipX: Float, h: Float): Path = Path().apply {
    moveTo(leftX, h * 0.2f)
    lineTo(tipX, h * 0.5f)
    lineTo(leftX, h * 0.8f)
    close()
}

private fun triangleLeft(rightX: Float, tipX: Float, h: Float): Path = Path().apply {
    moveTo(rightX, h * 0.2f)
    lineTo(tipX, h * 0.5f)
    lineTo(rightX, h * 0.8f)
    close()
}

private fun formatTime(ms: Long): String {
    val totalSec = (ms / 1000L).toInt()
    return "%d:%02d".format(totalSec / 60, totalSec % 60)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun TurntablePreview() {
    TurntableScreen(PreviewNowPlaying, NoopControls)
}
