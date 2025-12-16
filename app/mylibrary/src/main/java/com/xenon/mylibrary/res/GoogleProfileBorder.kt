package com.xenon.mylibrary.res

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun GoogleProfilBorder(
    isSignedIn: Boolean,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 2.5.dp,
    gapAngle: Float = 15f,
    angleChangeIntervalMillis: Long = 2000L,
    sweepAnimationSpec: AnimationSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessVeryLow
    )
) {
    val googleColors = remember {
        listOf(
            Color(0xFF4285F4), // Blue
            Color(0xFFDB4437), // Red
            Color(0xFFF4B400), // Yellow
            Color(0xFF0F9D58)  // Green
        )
    }
    val greyColor = Color.Gray.copy(alpha = 0.6f)
    val numColors = googleColors.size

    // Animate each segment color based on sign-in status
    val animatedColors = List(numColors) { index ->
        animateColorAsState(
            targetValue = if (isSignedIn) googleColors[index] else greyColor,
            animationSpec = tween(1000),
            label = "SegmentColor$index"
        ).value
    }

    val rotationAngleAnim = remember { Animatable(0f) }

    val sweepAngleAnimatables = remember {
        List(numColors) { Animatable(0f) }
    }

    val equalSweepAngle = (360f - numColors * gapAngle) / numColors

    var targetSweepAngles by remember {
        mutableStateOf(List(numColors) { equalSweepAngle })
    }

    // Continuous rotation when signed in
    LaunchedEffect(isSignedIn) {
        if (isSignedIn) {
            rotationAngleAnim.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 4000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            rotationAngleAnim.stop()
            rotationAngleAnim.snapTo(0f)
        }
    }

    // Randomly vary segment sizes every few seconds when signed in
    LaunchedEffect(isSignedIn, angleChangeIntervalMillis) {
        if (isSignedIn) {
            while (true) {
                targetSweepAngles = generateRandomSweepAngles(numColors, gapAngle)
                delay(angleChangeIntervalMillis)
            }
        } else {
            targetSweepAngles = List(numColors) { equalSweepAngle }
        }
    }

    // Animate sweep angles toward current targets
    LaunchedEffect(targetSweepAngles) {
        coroutineScope {
            targetSweepAngles.forEachIndexed { index, targetAngle ->
                launch {
                    sweepAngleAnimatables[index].animateTo(
                        targetValue = targetAngle,
                        animationSpec = sweepAnimationSpec
                    )
                }
            }
        }
    }

    val density = LocalDensity.current
    val strokeWidthPx = with(density) { strokeWidth.toPx() }

    Canvas(modifier = modifier) {
        val canvasSize = size.minDimension
        @Suppress("UnusedVariable", "unused") val radius = canvasSize / 2f - strokeWidthPx / 2f

        var currentStartAngle = rotationAngleAnim.value - 90f

        for (i in animatedColors.indices) {
            val sweep = sweepAngleAnimatables[i].value
            if (sweep <= 0.1f) continue

            drawArc(
                color = animatedColors[i],
                startAngle = currentStartAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(
                    x = (size.width - canvasSize) / 2f,
                    y = (size.height - canvasSize) / 2f
                ),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            currentStartAngle += sweep + gapAngle
        }
    }
}

// Helper: generate random but balanced arc lengths
private fun generateRandomSweepAngles(count: Int, gapAngle: Float): List<Float> {
    if (count <= 0) return emptyList()

    val totalGap = count * gapAngle
    val totalSweep = (360f - totalGap).coerceAtLeast(0f)
    if (totalSweep <= 0f) return List(count) { 0f }

    // Random values biased toward larger arcs (10°–100°)
    val randoms = List(count) { Random.nextFloat() * 90f + 10f }
    val sum = randoms.sum()

    return if (sum == 0f) {
        List(count) { totalSweep / count }
    } else {
        randoms.map { (it / sum) * totalSweep }
    }
}