package com.charan.norton.common.theme

import androidx.compose.ui.graphics.Color

// Light Theme
// Brand Colors
val PrimaryColorLight = Color(0xFF5B4BE1)
val PrimaryContainerLight = Color(0xFFEEEBF8)
val PrimaryEdgeLight = Color(0xFFD6CFF7)

// Status Colors
val SafeColorLight = Color(0xFF1F8F5C)
val WarningColorLight = Color(0xFFB7791F)
val DangerColorLight = Color(0xFFC8324A)

// Neutral Colors - Light
val BgColorLight = Color(0xFFF6F5FA)
val SurfaceColorLight = Color(0xFFFFFFFF)
val OutlineColorLight = Color(0xFFE3E1EC)
val InkColorLight = Color(0xFF1A1B26)
val SurfaceVariantLight = Color(0xFFF0F0F0)



// Dark Theme
// Brand Colors - Dark
val PrimaryColorDark = Color(0xFFC5B2FF)
val PrimaryContainerDark = Color(0xFF3E2E7A)
val PrimaryEdgeDark = Color(0xFF7B68C4)

// Status Colors - Dark
val SafeColorDark = Color(0xFF4CAF50)
val WarningColorDark = Color(0xFFD4A574)
val DangerColorDark = Color(0xFFEF9A9A)

// Neutral Colors - Dark
val BgColorDark = Color(0xFF0F0E14)
val SurfaceColorDark = Color(0xFF1A1826)
val OutlineColorDark = Color(0xFF3A3847)
val InkColorDark = Color(0xFFFAF9FE)
val SurfaceVariantDark = Color(0xFF2A2837)

// Colors for Material3 - Light
object NortonColorsLight {
    val primary = PrimaryColorLight
    val primaryContainer = PrimaryContainerLight
    val primaryEdge = PrimaryEdgeLight
    val success = SafeColorLight
    val warning = WarningColorLight
    val error = DangerColorLight
    val background = BgColorLight
    val surface = SurfaceColorLight
    val outline = OutlineColorLight
    val onSurface = InkColorLight
    val surfaceVariant = SurfaceVariantLight
    val onPrimary = Color(0xFFFFFFFF)
    val onPrimaryContainer = PrimaryColorLight
    val errorContainer = Color(0xFFFCE4EC)
    val onError = Color(0xFFFFFFFF)
    val warningContainer = Color(0xFFFFF3E0)
    val onWarning = Color(0xFFFFFFFF)
}

// Colors for Material3 - Dark
object NortonColorsDark {
    val primary = PrimaryColorDark
    val primaryContainer = PrimaryContainerDark
    val primaryEdge = PrimaryEdgeDark
    val success = SafeColorDark
    val warning = WarningColorDark
    val error = DangerColorDark
    val background = BgColorDark
    val surface = SurfaceColorDark
    val outline = OutlineColorDark
    val onSurface = InkColorDark
    val surfaceVariant = SurfaceVariantDark
    val onPrimary = Color(0xFF2A1D5C)
    val onPrimaryContainer = PrimaryColorDark
    val errorContainer = Color(0xFF5E2A2A)
    val onError = Color(0xFF000000)
    val warningContainer = Color(0xFF5C4033)
    val onWarning = Color(0xFF000000)
}