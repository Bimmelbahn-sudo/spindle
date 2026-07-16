package com.markfoundry.spindle.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Shared Canvas-drawn transport glyphs, used by every skin. */

@Composable
fun PlayIcon(tint: Color, dimension: Dp = 24.dp) {
    Canvas(Modifier.size(dimension)) {
        val w = size.width
        val h = size.height
        val p = Path().apply {
            moveTo(w * 0.24f, h * 0.16f)
            lineTo(w * 0.84f, h * 0.5f)
            lineTo(w * 0.24f, h * 0.84f)
            close()
        }
        drawPath(p, tint)
    }
}

@Composable
fun PauseIcon(tint: Color, dimension: Dp = 24.dp) {
    Canvas(Modifier.size(dimension)) {
        val w = size.width
        val h = size.height
        val barW = w * 0.22f
        val radius = CornerRadius(2.dp.toPx())
        drawRoundRect(tint, Offset(w * 0.2f, h * 0.16f), Size(barW, h * 0.68f), radius)
        drawRoundRect(tint, Offset(w * 0.58f, h * 0.16f), Size(barW, h * 0.68f), radius)
    }
}

@Composable
fun NextIcon(tint: Color, dimension: Dp = 24.dp) {
    Canvas(Modifier.size(dimension)) {
        val w = size.width
        val h = size.height
        drawPath(triRight(w * 0.10f, w * 0.48f, h), tint)
        drawPath(triRight(w * 0.44f, w * 0.82f, h), tint)
        drawRoundRect(tint, Offset(w * 0.84f, h * 0.22f), Size(w * 0.10f, h * 0.56f), CornerRadius(1.5.dp.toPx()))
    }
}

@Composable
fun PrevIcon(tint: Color, dimension: Dp = 24.dp) {
    Canvas(Modifier.size(dimension)) {
        val w = size.width
        val h = size.height
        drawRoundRect(tint, Offset(w * 0.06f, h * 0.22f), Size(w * 0.10f, h * 0.56f), CornerRadius(1.5.dp.toPx()))
        drawPath(triLeft(w * 0.90f, w * 0.52f, h), tint)
        drawPath(triLeft(w * 0.56f, w * 0.18f, h), tint)
    }
}

private fun triRight(leftX: Float, tipX: Float, h: Float): Path = Path().apply {
    moveTo(leftX, h * 0.2f)
    lineTo(tipX, h * 0.5f)
    lineTo(leftX, h * 0.8f)
    close()
}

private fun triLeft(rightX: Float, tipX: Float, h: Float): Path = Path().apply {
    moveTo(rightX, h * 0.2f)
    lineTo(tipX, h * 0.5f)
    lineTo(rightX, h * 0.8f)
    close()
}
