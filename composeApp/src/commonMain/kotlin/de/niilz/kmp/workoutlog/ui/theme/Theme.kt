package de.niilz.kmp.workoutlog.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue500 = Color(0xFF2196F3)
val Blue700 = Color(0xFF1976D2)
val Blue900 = Color(0xFF0D47A1)
val Green500 = Color(0xFF4CAF50)
val Green100 = Color(0xFFC8E6C9)
val Red500 = Color(0xFFF44336)

private val LightColorScheme = lightColorScheme(
    primary = Blue700,
    onPrimary = Color.White,
    primaryContainer = Blue500,
    onPrimaryContainer = Color.White,
    secondary = Green500,
    secondaryContainer = Green100,
    error = Red500,
)

@Composable
fun WorkoutLogTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content,
    )
}
