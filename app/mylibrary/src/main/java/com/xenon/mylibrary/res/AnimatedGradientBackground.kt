package com.xenon.mylibrary.res

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AnimatedGradientBackground(
    modifier: Modifier = Modifier,
    colors: List<Color>? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        BlurryBlobsBackground(
            modifier = Modifier.fillMaxSize(),
            colors = colors,
            backgroundColor = backgroundColor,
        )
        content()
    }
}

@Composable
fun BlurryBlobsBackground(
    modifier: Modifier = Modifier,
    colors: List<Color>? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    val colorScheme = MaterialTheme.colorScheme
    val resolvedColors = remember(colors, colorScheme) {
        colors ?: listOf(
            colorScheme.primary.copy(alpha = 0.35f),
            colorScheme.secondary.copy(alpha = 0.25f),
            colorScheme.tertiary.copy(alpha = 0.3f),
            colorScheme.primaryContainer.copy(alpha = 0.2f),
        )
    }

    val blobs = remember(resolvedColors) { resolvedColors.map { Blob(it) } }

    blobs.forEach { blob ->
        LaunchedEffect(blob) {
            blob.animate()
        }
    }

    Canvas(modifier = modifier.background(backgroundColor)) {
        blobs.forEach { blob ->
            // A larger radius creates a softer, more spread-out "blur" effect
            val radius = size.minDimension * 0.8f
            val center = Offset(blob.x.value * size.width, blob.y.value * size.height)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(blob.color, Color.Transparent),
                    center = center,
                    radius = radius,
                ),
                radius = radius,
                center = center,
            )
        }
    }
}

private class Blob(val color: Color) {
    val x = Animatable(Random.nextFloat())
    val y = Animatable(Random.nextFloat())

    suspend fun animate() = coroutineScope {
        // This will launch two infinite loops, one for x and one for y.
        // They will run concurrently, creating smooth, diagonal movement.
        launch {
            while (true) {
                x.animateTo(
                    targetValue = Random.nextFloat(),
                    animationSpec = tween(
                        durationMillis = Random.nextInt(10_000, 20_000),
                        easing = LinearEasing,
                    ),
                )
            }
        }
        launch {
            while (true) {
                y.animateTo(
                    targetValue = Random.nextFloat(),
                    animationSpec = tween(
                        durationMillis = Random.nextInt(10_000, 20_000),
                        easing = LinearEasing,
                    ),
                )
            }
        }
    }
}
