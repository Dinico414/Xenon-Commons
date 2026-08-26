package com.xenon.mylibrary.res

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.cos
import kotlin.math.sin

/**
 * A wrapper that adds an animated background with moving, blurred orbs.
 * Perfect for welcome and permission screens.
 */
@Composable
fun AnimatedGradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orb_animation")
    
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    val colorScheme = MaterialTheme.colorScheme
    // Base color for the background
    val baseColor = colorScheme.surface
    
    // Orb colors based on the theme
    val orbColor1 = colorScheme.primary.copy(alpha = 0.35f)
    val orbColor2 = colorScheme.secondary.copy(alpha = 0.25f)
    val orbColor3 = colorScheme.tertiary.copy(alpha = 0.3f)
    val orbColor4 = colorScheme.primaryContainer.copy(alpha = 0.2f)

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.99f)) {
            drawRect(color = baseColor)
            
            val width = size.width
            val height = size.height
            val centerX = width / 2
            val centerY = height / 2

            // Orb 1: Larger, slower, primary
            val x1 = centerX + cos(time * 2 * Math.PI.toFloat()) * (width * 0.35f)
            val y1 = centerY + sin(time * 2 * Math.PI.toFloat() * 0.5f) * (height * 0.25f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(orbColor1, Color.Transparent),
                    center = Offset(x1, y1),
                    radius = width * 0.7f
                ),
                center = Offset(x1, y1),
                radius = width * 0.7f
            )

            // Orb 2: Medium, faster, secondary
            val x2 = centerX + sin(time * 2 * Math.PI.toFloat() + 2f) * (width * 0.45f)
            val y2 = centerY + cos(time * 2 * Math.PI.toFloat() * 1.2f + 1f) * (height * 0.35f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(orbColor2, Color.Transparent),
                    center = Offset(x2, y2),
                    radius = width * 0.55f
                ),
                center = Offset(x2, y2),
                radius = width * 0.55f
            )

            // Orb 3: Medium-Large, tertiary
            val x3 = centerX + cos(time * 2 * Math.PI.toFloat() * 0.8f + 4f) * (width * 0.3f)
            val y3 = centerY + sin(time * 2 * Math.PI.toFloat() + 3f) * (height * 0.45f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(orbColor3, Color.Transparent),
                    center = Offset(x3, y3),
                    radius = width * 0.65f
                ),
                center = Offset(x3, y3),
                radius = width * 0.65f
            )
            
            // Orb 4: Smallest, primaryContainer
            val x4 = centerX + sin(time * 2 * Math.PI.toFloat() * 1.5f) * (width * 0.2f)
            val y4 = centerY + cos(time * 2 * Math.PI.toFloat() * 0.7f) * (height * 0.3f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(orbColor4, Color.Transparent),
                    center = Offset(x4, y4),
                    radius = width * 0.4f
                ),
                center = Offset(x4, y4),
                radius = width * 0.4f
            )
        }
        content()
    }
}
