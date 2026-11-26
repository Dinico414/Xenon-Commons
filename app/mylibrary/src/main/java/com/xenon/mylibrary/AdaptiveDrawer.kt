@file:Suppress("unused")

package com.xenon.mylibrary

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.xenon.mylibrary.theme.LocalDeviceConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/** How the navigation drawer is presented for the current window size or device mode. */
enum class DrawerMode {
    /** Slides in above the content behind a scrim. Used on phones. */
    Overlay,

    /** Sits beside the content in the layout and insets it. Used on foldables, tablets, and desktop. */
    Implemented;

    companion object {
        @Deprecated("Renamed to Implemented", ReplaceWith("DrawerMode.Implemented"))
        val Docked: DrawerMode get() = Implemented
    }
}

/** Window width at or above which the drawer is implemented into layout instead of overlaying. */
val DrawerDockedBreakpoint: Dp = 600.dp

private const val HalfOpenFraction = 0.5f

private val DrawerSpring = spring<Float>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMediumLow,
)

/**
 * Holds reveal progress and presentation mode for an adaptive navigation drawer.
 *
 * A single drawer instance animates seamlessly between [DrawerMode.Overlay] and [DrawerMode.Implemented]
 * without recreating composables or losing state.
 *
 * [fraction] is `0f` when hidden and `1f` when fully revealed.
 */
@Stable
class AdaptiveDrawerState internal constructor(
    initialMode: DrawerMode = DrawerMode.Overlay,
    initiallyOpen: Boolean = false,
) {
    var currentMode: DrawerMode by mutableStateOf(initialMode)
        internal set

    private val anchor = Animatable(if (initiallyOpen) 1f else 0f)

    val fraction: Float get() = anchor.value

    val isOpen: Boolean get() = anchor.value >= HalfOpenFraction

    val isImplemented: Boolean get() = (currentMode == DrawerMode.Implemented) && isOpen

    val isOverlay: Boolean get() = (currentMode == DrawerMode.Overlay) && isOpen

    suspend fun openOverlay() {
        currentMode = DrawerMode.Overlay
        anchor.animateTo(1f, DrawerSpring)
    }

    suspend fun implement() {
        currentMode = DrawerMode.Implemented
        anchor.animateTo(1f, DrawerSpring)
    }

    suspend fun open() {
        anchor.animateTo(1f, DrawerSpring)
    }

    suspend fun close() {
        anchor.animateTo(0f, DrawerSpring)
    }

    /**
     * Cycles drawer state with a single button:
     * - On phone (`isWideScreen == false`): Closed -> Overlay -> Closed
     * - On foldable/tablet (`isWideScreen == true`): Closed -> Expand (Overlay) -> Implement (Implemented) -> Collapse (Closed)
     */
    suspend fun toggleAdaptive(isWideScreen: Boolean) {
        if (!isWideScreen) {
            if (isOpen) close() else openOverlay()
        } else {
            if (!isOpen) {
                openOverlay()
            } else if (currentMode == DrawerMode.Overlay) {
                implement()
            } else {
                close()
            }
        }
    }

    suspend fun toggle() {
        if (isOpen) close() else open()
    }

    internal suspend fun dragTo(value: Float) = anchor.snapTo(value.coerceIn(0f, 1f))

    internal suspend fun settle(target: Float) {
        if (target >= HalfOpenFraction) open() else close()
    }
}

private val AdaptiveDrawerStateSaver = Saver<AdaptiveDrawerState, Pair<Boolean, String>>(
    save = { Pair(it.isOpen, it.currentMode.name) },
) { (isOpen, modeName) ->
    val mode = runCatching { DrawerMode.valueOf(modeName) }.getOrDefault(DrawerMode.Overlay)
    AdaptiveDrawerState(initialMode = mode, initiallyOpen = isOpen)
}

@Composable
fun rememberAdaptiveDrawerState(
    initialMode: DrawerMode = DrawerMode.Overlay,
    initiallyOpen: Boolean = false,
): AdaptiveDrawerState =
    rememberSaveable(saver = AdaptiveDrawerStateSaver) {
        AdaptiveDrawerState(initialMode = initialMode, initiallyOpen = initiallyOpen)
    }

/**
 * Hosts a single [drawerContent] next to [content].
 *
 * Smoothly interpolates between:
 * - [DrawerMode.Overlay]: drawer slides over content behind a scrim.
 * - [DrawerMode.Implemented]: drawer sits beside content in layout. On dual-screen / Surface Duo spanned
 *   mode (`isSpannedMode == true`), content splits 50/50 across displays with a central hinge gap.
 */
@Composable
fun AdaptiveDrawerLayout(
    modifier: Modifier = Modifier,
    mode: DrawerMode? = null,
    state: AdaptiveDrawerState = rememberAdaptiveDrawerState(),
    drawerWidth: Dp = 360.dp,
    isSpannedMode: Boolean? = null,
    spannedHingeGap: Dp? = null,
    scrimColor: Color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
    drawerContent: @Composable (mode: DrawerMode) -> Unit,
    content: @Composable () -> Unit,
) {
    val deviceConfig = LocalDeviceConfig.current
    val activeSpanned = isSpannedMode ?: deviceConfig.isSpannedMode
    val activeHingeGap = spannedHingeGap ?: deviceConfig.hingeGapDp

    val scope = rememberCoroutineScope()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val totalWidth = maxWidth
        val effectiveMode = mode ?: state.currentMode

        val implementedDrawerWidth = if (activeSpanned) {
            ((totalWidth - activeHingeGap) / 2f).coerceAtLeast(0.dp)
        } else {
            drawerWidth
        }

        val implementedContentInset = if (activeSpanned) {
            ((totalWidth + activeHingeGap) / 2f).coerceAtLeast(0.dp)
        } else {
            drawerWidth
        }

        val targetDock = if (effectiveMode == DrawerMode.Implemented) 1f else 0f
        val dockAnchor = remember { Animatable(targetDock) }

        LaunchedEffect(effectiveMode) {
            dockAnchor.animateTo(targetDock, DrawerSpring)
        }

        val fraction = state.fraction
        val docked = dockAnchor.value

        val activeDrawerWidth = lerp(drawerWidth, implementedDrawerWidth, docked)
        val activeContentInset = lerp(0.dp, implementedContentInset, fraction * docked)
        val drawerWidthPx = with(LocalDensity.current) { activeDrawerWidth.toPx() }
        val drawerOffset = -(1f - fraction) * drawerWidthPx * (1f - docked)
        val scrimAlpha = fraction * (1f - docked)

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = activeContentInset),
            ) {
                content()
            }

            if (scrimAlpha > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { alpha = scrimAlpha }
                        .background(scrimColor)
                        .pointerInput(Unit) {
                            detectTapGestures { scope.launch { state.close() } }
                        },
                )
            }

            Box(
                modifier = Modifier
                    .width(activeDrawerWidth)
                    .fillMaxHeight()
                    .offset { IntOffset(drawerOffset.roundToInt(), 0) }
                    .drawerDragHandle(state, drawerWidthPx, scope),
            ) {
                drawerContent(effectiveMode)
            }

            if ((docked < 1f) && (fraction < 1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(EdgeSwipeWidth)
                        .drawerDragHandle(state, drawerWidthPx, scope),
                )
            }

            BackHandler(enabled = (docked < 1f) && state.isOpen) {
                scope.launch { state.close() }
            }
        }
    }
}

private val EdgeSwipeWidth: Dp = 24.dp

private fun Modifier.drawerDragHandle(
    state: AdaptiveDrawerState,
    drawerWidthPx: Float,
    scope: CoroutineScope,
): Modifier = pointerInput(state, drawerWidthPx) {
    var startFraction = 0f
    var dragged = 0f

    detectHorizontalDragGestures(
        onDragStart = {
            startFraction = state.fraction
            dragged = 0f
        },
        onDragEnd = { scope.launch { state.settle(startFraction + (dragged / drawerWidthPx)) } },
        onDragCancel = { scope.launch { state.settle(startFraction + (dragged / drawerWidthPx)) } },
    ) { change, dragAmount ->
        change.consume()
        dragged += dragAmount
        scope.launch { state.dragTo(startFraction + (dragged / drawerWidthPx)) }
    }
}
