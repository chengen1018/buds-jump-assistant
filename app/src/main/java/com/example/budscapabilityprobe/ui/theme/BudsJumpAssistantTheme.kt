package com.example.budscapabilityprobe.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColors = lightColorScheme(
    primary = Color(0xFF365BDD),
    onPrimary = Color.White,
    background = Color(0xFFF7F7FA),
    onBackground = Color(0xFF17181C),
    surface = Color.White,
    onSurface = Color(0xFF17181C),
    surfaceVariant = Color(0xFFEDEDF2),
    onSurfaceVariant = Color(0xFF686B75),
)

@Composable
fun BudsJumpAssistantTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColors,
        typography = MaterialTheme.typography,
        content = content,
    )
}

