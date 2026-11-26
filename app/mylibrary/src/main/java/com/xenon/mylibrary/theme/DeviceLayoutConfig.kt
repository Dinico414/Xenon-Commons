package com.xenon.mylibrary.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DeviceLayoutConfig(
    val isWing: Boolean,
    val isCommunicator: Boolean,
    val isMindOne: Boolean,
    val isSurfaceDuo: Boolean,
    val isSpannedMode: Boolean,
    val hingeGapDp: Dp,
    val fabOnLeft: Boolean,
    val toggleFabSide: () -> Unit
)

val LocalDeviceConfig = compositionLocalOf {
    DeviceLayoutConfig(
        isWing = false,
        isCommunicator = false,
        isMindOne = false,
        isSurfaceDuo = false,
        isSpannedMode = false,
        hingeGapDp = 0.dp,
        fabOnLeft = true,
        toggleFabSide = {}
    )
}
