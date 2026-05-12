package com.charan.norton.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.charan.norton.R

// Poppins font family
val PoppinsFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold)
)

val NortonTypography = Typography(
    // Display - 36sp, weight 600
    displayLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 36.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    // Headline / Title Large - 28sp, weight 600
    headlineLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    // Headline Medium / Title Medium - 22sp, weight 600
    headlineMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Headline Small - 20sp, weight 600
    headlineSmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Title Large - 18sp, weight 600
    titleLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    // Title Medium - 16sp, weight 600
    titleMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),

    // Title Small - 14sp, weight 600
    titleSmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Body Large - 15sp, weight 500
    bodyLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Body Medium - 14sp, weight 500
    bodyMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    // Body Small - 12sp, weight 500
    bodySmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // Label Large - 14sp, weight 700
    labelLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Label Medium - 12sp, weight 700
    labelMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    // Label Small - 11sp, weight 700
    labelSmall = TextStyle(
        fontFamily = PoppinsFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
