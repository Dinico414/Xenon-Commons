package com.xenon.mylibrary.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

public data class DeviceLayoutConfig(
    val isSurfaceDuo: Boolean,
    val isSpannedMode: Boolean,
    val hingeGapDp: Dp,
    val fabOnLeft: Boolean,
    val toggleFabSide: () -> Unit
)

public val LocalDeviceConfig = compositionLocalOf<DeviceLayoutConfig> {
    error("No DeviceLayoutConfig provided. Wrap your UI with DeviceConfigProvider.")
}