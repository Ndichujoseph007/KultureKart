package com.ndichu.kulturekart.ui.theme



import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = GoldLightPrimary,
    onPrimary = GoldLightOnPrimary,
    primaryContainer = GoldLightPrimaryContainer,
    onPrimaryContainer = GoldLightOnPrimaryContainer,
    secondary = GoldLightSecondary,
    onSecondary = GoldLightOnSecondary,
    secondaryContainer = GoldLightSecondaryContainer,
    onSecondaryContainer = GoldLightOnSecondaryContainer,
    tertiary = GoldLightTertiary,
    onTertiary = GoldLightOnTertiary,
    tertiaryContainer = GoldLightTertiaryContainer,
    onTertiaryContainer = GoldLightOnTertiaryContainer,
    background = GoldLightBackground,
    onBackground = GoldLightOnBackground,
    surface = GoldLightSurface,
    onSurface = GoldLightOnSurface,
    surfaceVariant = GoldLightSurfaceVariant,
    onSurfaceVariant = GoldLightOnSurfaceVariant,
    outline = GoldLightOutline,
    error = GoldLightError,
    onError = GoldLightOnError,
    errorContainer = GoldLightErrorContainer,
    onErrorContainer = GoldLightOnErrorContainer,
)

private val DarkColorScheme = darkColorScheme(
    primary = GoldDarkPrimary,
    onPrimary = GoldDarkOnPrimary,
    primaryContainer = GoldDarkPrimaryContainer,
    onPrimaryContainer = GoldDarkOnPrimaryContainer,
    secondary = GoldDarkSecondary,
    onSecondary = GoldDarkOnSecondary,
    secondaryContainer = GoldDarkSecondaryContainer,
    onSecondaryContainer = GoldDarkOnSecondaryContainer,
    tertiary = GoldDarkTertiary,
    onTertiary = GoldDarkOnTertiary,
    tertiaryContainer = GoldDarkTertiaryContainer,
    onTertiaryContainer = GoldDarkOnTertiaryContainer,
    background = GoldDarkBackground,
    onBackground = GoldDarkOnBackground,
    surface = GoldDarkSurface,
    onSurface = GoldDarkOnSurface,
    surfaceVariant = GoldDarkSurfaceVariant,
    onSurfaceVariant = GoldDarkOnSurfaceVariant,
    outline = GoldDarkOutline,
    error = GoldDarkError,
    onError = GoldDarkOnError,
    errorContainer = GoldDarkErrorContainer,
    onErrorContainer = GoldDarkOnErrorContainer,
)


@Composable
fun KultureKartThemeWrapper(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,

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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}



