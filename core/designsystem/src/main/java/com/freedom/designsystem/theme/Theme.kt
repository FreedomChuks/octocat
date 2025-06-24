package com.freedom.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = pinkText,
    surface = blueBGNight,
    onSurface = pinkText,
    background = cardNight
)

private val LightColorScheme = lightColorScheme(
    primary = blueText,
    surface = blueBG,
    background = White,
    onSurface = blueText
)

@Composable
fun OctocatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!darkTheme) LightColorScheme else DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}