package com.markfoundry.spindle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Coral = Color(0xFFE8645A)
private val Navy = Color(0xFF2B356A)
private val Cream = Color(0xFFF2E7D0)
private val Red = Color(0xFFD94B3F)

@Composable
fun OnboardingScreen(onGrantClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Coral)
            .systemBarsPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("SPINDLE", color = Navy, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, letterSpacing = 3.sp)
        Spacer(Modifier.height(20.dp))
        Text("One tap and you're set", color = Navy, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        Text(
            "Spindle reads your phone's media controls to show and control whatever's playing — Spotify, YouTube Music, anything. No account, no login.",
            color = Navy.copy(alpha = 0.82f),
            fontSize = 15.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(28.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Navy)
                .padding(18.dp),
        ) {
            Text("Notification access", color = Cream, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                "The same permission smartwatches use to control media. You grant it once.",
                color = Cream.copy(alpha = 0.75f),
                fontSize = 12.sp,
                lineHeight = 17.sp,
            )
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onGrantClick,
            colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Cream),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text("Grant access", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
fun NothingPlayingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Coral)
            .systemBarsPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("SPINDLE", color = Navy, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, letterSpacing = 3.sp)
        Spacer(Modifier.height(16.dp))
        Text("Nothing playing", color = Navy, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            "Start a track in Spotify (or any player) and it'll spin up here.",
            color = Navy.copy(alpha = 0.82f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
    }
}
