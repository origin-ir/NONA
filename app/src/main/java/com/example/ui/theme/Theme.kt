package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NonaColorScheme = darkColorScheme(
    primary = ZenPurplePrimary,
    onPrimary = CosmicBackground,
    primaryContainer = ZenPurpleSecondary,
    onPrimaryContainer = Color.White,
    secondary = ZenTeal,
    onSecondary = CosmicBackground,
    secondaryContainer = Color(0xFF134E4A),
    onSecondaryContainer = ZenTeal,
    tertiary = ZenGold,
    onTertiary = CosmicBackground,
    background = CosmicBackground,
    onBackground = TextPrimary,
    surface = CosmicSurface,
    onSurface = TextPrimary,
    surfaceVariant = CosmicSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFF3B3363)
)

@Composable
fun NonaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NonaColorScheme,
        typography = Typography,
        content = content
    )
}

// Backward compatibility alias for template tests
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    NonaTheme(content = content)
}
