package com.example.app.view.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Define the colors here if they aren't in a separate Color.kt file
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Your Custom Project Colors
val CoffeeBrown = Color(0xFF4E342E)
val CoffeeCream = Color(0xFFD7CCC8)
val SoftCream = Color(0xFFF5F5F5)
val White = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
    primary = CoffeeBrown,
    secondary = CoffeeCream,
    tertiary = Pink80,
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = CoffeeBrown,
    secondary = CoffeeCream,
    tertiary = Pink40,
    background = SoftCream,
    surface = White,
    onPrimary = White,
    onSecondary = CoffeeBrown,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Set to false to prioritize your custom brand colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Ensure Typography is defined in your Typography.kt file
        content = content
    )
}