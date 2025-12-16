package com.xenon.mylibrary.theme

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@SuppressLint("LocalContextResourcesRead")
@Suppress("unused", "AssignedValueIsNeverRead")
@Composable
fun DeviceConfigProvider(
    appSize: IntSize, // You already have this
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    LocalDensity.current

    /* === Surface Duo Values === */
    val modelUpper = remember { Build.MODEL.uppercase() }
    val duoGeneration = remember {
        when {
            modelUpper.contains("SURFACE DUO 2") -> "2"
            modelUpper.contains("SURFACE DUO") && !modelUpper.contains("SURFACE DUO 2") -> "1"
            else -> null
        }
    }
    val isSurfaceDuo = duoGeneration != null

    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val displayMetrics = context.resources.displayMetrics
    val screenWidthPx = if (isLandscape) displayMetrics.widthPixels else displayMetrics.heightPixels

    val expectedSpannedWidth = when (duoGeneration) {
        "1" -> 2784
        "2" -> 2754
        else -> 0
    }

    val isSpannedMode = isSurfaceDuo &&
            isLandscape &&
            screenWidthPx >= expectedSpannedWidth - 100

    val hingeGapPx = when (duoGeneration) {
        "1" -> 84
        "2" -> 66
        else -> 0
    }
    val hingeGapDp = (hingeGapPx / displayMetrics.density).dp

    var fabOnLeft by rememberSaveable { mutableStateOf(true) }

    val config = remember(isSurfaceDuo, isSpannedMode, hingeGapDp, fabOnLeft) {
        DeviceLayoutConfig(
            isSurfaceDuo = isSurfaceDuo,
            isSpannedMode = isSpannedMode,
            hingeGapDp = hingeGapDp,
            fabOnLeft = fabOnLeft,
            toggleFabSide = { fabOnLeft = !fabOnLeft }
        )
    }

    CompositionLocalProvider(LocalDeviceConfig provides config) {
        content()
    }
}
