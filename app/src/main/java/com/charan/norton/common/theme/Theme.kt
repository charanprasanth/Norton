package com.charan.norton.common.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = NortonColorsLight.primary,
    onPrimary = NortonColorsLight.onPrimary,
    primaryContainer = NortonColorsLight.primaryContainer,
    onPrimaryContainer = NortonColorsLight.onPrimaryContainer,
    secondary = NortonColorsLight.primary,
    onSecondary = NortonColorsLight.onPrimary,
    secondaryContainer = NortonColorsLight.primaryContainer,
    onSecondaryContainer = NortonColorsLight.onPrimaryContainer,
    tertiary = NortonColorsLight.primary,
    onTertiary = NortonColorsLight.onPrimary,
    tertiaryContainer = NortonColorsLight.primaryContainer,
    onTertiaryContainer = NortonColorsLight.onPrimaryContainer,
    error = NortonColorsLight.error,
    onError = NortonColorsLight.onError,
    errorContainer = NortonColorsLight.errorContainer,
    onErrorContainer = NortonColorsLight.error,
    background = NortonColorsLight.background,
    onBackground = NortonColorsLight.onSurface,
    surface = NortonColorsLight.surface,
    onSurface = NortonColorsLight.onSurface,
    surfaceVariant = NortonColorsLight.surfaceVariant,
    onSurfaceVariant = NortonColorsLight.onSurface,
    outline = NortonColorsLight.outline,
    outlineVariant = NortonColorsLight.primaryEdge,
    scrim = NortonColorsLight.onSurface,
    surfaceTint = transparent
)

// Dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = NortonColorsDark.primary,
    onPrimary = NortonColorsDark.onPrimary,
    primaryContainer = NortonColorsDark.primaryContainer,
    onPrimaryContainer = NortonColorsDark.onPrimaryContainer,
    secondary = NortonColorsDark.primary,
    onSecondary = NortonColorsDark.onPrimary,
    secondaryContainer = NortonColorsDark.primaryContainer,
    onSecondaryContainer = NortonColorsDark.onPrimaryContainer,
    tertiary = NortonColorsDark.primary,
    onTertiary = NortonColorsDark.onPrimary,
    tertiaryContainer = NortonColorsDark.primaryContainer,
    onTertiaryContainer = NortonColorsDark.onPrimaryContainer,
    error = NortonColorsDark.error,
    onError = NortonColorsDark.onError,
    errorContainer = NortonColorsDark.errorContainer,
    onErrorContainer = NortonColorsDark.error,
    background = NortonColorsDark.background,
    onBackground = NortonColorsDark.onSurface,
    surface = NortonColorsDark.surface,
    onSurface = NortonColorsDark.onSurface,
    surfaceVariant = NortonColorsDark.surfaceVariant,
    onSurfaceVariant = NortonColorsDark.onSurface,
    outline = NortonColorsDark.outline,
    outlineVariant = NortonColorsDark.primaryEdge,
    scrim = NortonColorsDark.onSurface,
    surfaceTint = transparent
)

@Composable
fun NortonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic color (Android 12+)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Use custom theme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NortonTypography,
        content = content
    )
}