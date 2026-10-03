@file:Suppress("unused")

package com.xenon.mylibrary.res

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.ExtraLargeSpacing
import com.xenon.mylibrary.values.ExtraLargerCornerRadius
import com.xenon.mylibrary.values.IconSizeMedium
import com.xenon.mylibrary.values.LargeMediumElevation
import com.xenon.mylibrary.values.LargestPadding
import com.xenon.mylibrary.values.MediumSpacer
import com.xenon.mylibrary.values.MinTouchTargetSize
import com.xenon.mylibrary.values.NoElevation
import com.xenon.mylibrary.values.NoSpacing
import com.xenon.mylibrary.values.SmallElevation
import com.xenon.mylibrary.values.SmallPadding
import com.xenon.mylibrary.values.SmallerStroke
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

/**
 * Universal TopContentBar for search bars, title bars, and editable headers.
 */
@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun TopContentBar(
    modifier: Modifier = Modifier,
    // Outside paddings (Inside padding is fixed)
    outsidePadding: PaddingValues = PaddingValues(horizontal = LargestPadding, vertical = NoSpacing),
    // Haze and styling for the bar
    hazeState: HazeState? = null,
    blurEnabled: Boolean = true,
    containerColor: Color = colorScheme.surfaceContainer,
    elevation: Dp = if (blurEnabled) NoElevation else LargeMediumElevation,
    shape: Shape = CircleShape,
    hazeStyle: HazeStyle? = null,
    contentColor: Color = colorScheme.onSurface,
    // Navigation / Back / Cancel button
    onNavigationClick: (() -> Unit)? = null,
    navigationProgress: Float = 1f, // 1f = Back arrow, 0f = Close (X)
    navigationContentDescription: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    showNavigationButton: Boolean = onNavigationClick != null,
    // Content: Title / Search
    value: String = "",
    onValueChange: ((String) -> Unit)? = null,
    title: String? = null,
    placeholder: String = "",
    placeholderColor: Color = contentColor.copy(alpha = 0.6f),
    fontFamily: FontFamily = QuicksandTitleVariable,
    textStyle: TextStyle? = null,
    cursorBrush: Brush = SolidColor(colorScheme.primary),
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    focusRequester: FocusRequester? = null,
    onFocusChanged: ((FocusState) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    onBarClick: (() -> Unit)? = null,
    centerContent: (@Composable RowScope.() -> Unit)? = null,
    balanceTitle: Boolean = true,
    // Menu & Dropdown
    menuItems: List<MenuItem>? = null,
    menuExpanded: Boolean? = null,
    onMenuExpandedChange: ((Boolean) -> Unit)? = null,
    menuIcon: (@Composable () -> Unit)? = null,
    menuIconContentDescription: String? = null,
    menuRadius: Dp = ExtraLargerCornerRadius,
    menuWidthMin: Dp = 150.dp,
    menuWidthMax: Dp = 280.dp,
    menuShadowElevation: Dp = SmallElevation,
    menuAlignment: Alignment = Alignment.TopEnd,
    menuOffsetY: Dp = MediumSpacer,
    menuOffsetX: Dp = NoSpacing,
    menuMaxLines: Int = 1,
    menuMainContextFont: FontFamily = fontFamily,
    menuSubContextFont: FontFamily? = null,
    menuAnchorPos: Offset? = null,
    menuContainerColor: Color = colorScheme.surfaceContainer,
    menuHazeStyle: HazeStyle? = null,
    dropdownContent: (@Composable (expanded: Boolean, onDismiss: () -> Unit, anchor: Offset) -> Unit)? = null,
    // Trailing actions & callbacks
    actions: (@Composable RowScope.() -> Unit)? = null,
    onBarSizeChanged: ((IntSize) -> Unit)? = null,
    onBarAnchorChanged: ((Offset) -> Unit)? = null,
    onBarGloballyPositioned: ((LayoutCoordinates) -> Unit)? = null
) {
    var internalExpanded by remember { mutableStateOf(false) }
    val isExpanded = menuExpanded ?: internalExpanded
    val onDismissDropdown: () -> Unit = {
        if (menuExpanded == null) internalExpanded = false
        onMenuExpandedChange?.invoke(false)
    }

    var barAnchor by remember { mutableStateOf(Offset.Zero) }
    var isSearchFocused by remember { mutableStateOf(false) }

    val hasLeadingButton = showNavigationButton && onNavigationClick != null
    val hasTrailingMenu = !menuItems.isNullOrEmpty() || dropdownContent != null
    val hasTrailingActions = actions != null || hasTrailingMenu

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(outsidePadding)
    ) {
        Row(
            modifier = Modifier
                .graphicsLayer(clip = false)
                .fillMaxWidth()
                .onSizeChanged { onBarSizeChanged?.invoke(it) }
                .onGloballyPositioned { coords ->
                    val anchor = coords.positionInRoot().let { pos ->
                        Offset(pos.x + coords.size.width, pos.y + coords.size.height)
                    }
                    barAnchor = anchor
                    onBarAnchorChanged?.invoke(anchor)
                    onBarGloballyPositioned?.invoke(coords)
                }
                .shadow(elevation = elevation, shape = shape)
                .clip(shape)
                .background(containerColor)
                .then(
                    if (blurEnabled && hazeState != null) {
                        Modifier.hazeEffect(
                            state = hazeState,
                            style = hazeStyle ?: HazeMaterials.ultraThin()
                        )
                    } else Modifier
                )
                .pointerInput(Unit) {
                    detectDragGestures { _, _ -> }
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onBarClick?.invoke() }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading navigation button (fixed inside padding)
            if (hasLeadingButton) {
                val navDesc = navigationContentDescription ?: if (navigationProgress > 0.5f) "Back" else "Close"
                IconButton(
                    onClick = onNavigationClick,
                    modifier = Modifier
                        .padding(SmallPadding)
                        .semantics { contentDescription = navDesc }
                ) {
                    if (navigationIcon != null) {
                        navigationIcon()
                    } else {
                        MorphingBackCloseIcon(
                            progress = navigationProgress,
                            color = contentColor,
                            modifier = Modifier.size(IconSizeMedium)
                        )
                    }
                }
            } else if (balanceTitle && hasTrailingActions) {
                Spacer(modifier = Modifier.size(MinTouchTargetSize))
            }

            // Center: custom content, search text field, or title text
            if (centerContent != null) {
                centerContent()
            } else {
                val baseTextStyle = typography.titleLarge.merge(
                    TextStyle(
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center,
                        color = contentColor
                    )
                )
                val effectiveTextStyle = textStyle?.let { baseTextStyle.merge(it) } ?: baseTextStyle

                if (onValueChange != null) {
                    // Search / editable title mode
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .weight(1f)
                            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
                            .onFocusChanged {
                                isSearchFocused = it.isFocused
                                onFocusChanged?.invoke(it)
                            },
                        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                        singleLine = singleLine,
                        readOnly = readOnly,
                        enabled = enabled,
                        textStyle = effectiveTextStyle,
                        cursorBrush = cursorBrush,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (value.isEmpty() && placeholder.isNotEmpty() && !isSearchFocused) {
                                    Text(
                                        text = placeholder,
                                        style = effectiveTextStyle,
                                        color = placeholderColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                } else {
                    // Static title mode
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title ?: value,
                            style = effectiveTextStyle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Custom trailing actions
            if (actions != null) {
                actions()
            }

            // Trailing options menu button with XenonDropDown (fixed inside padding)
            if (hasTrailingMenu) {
                Box {
                    IconButton(
                        onClick = {
                            val target = !isExpanded
                            if (menuExpanded == null) internalExpanded = target
                            onMenuExpandedChange?.invoke(target)
                        },
                        modifier = Modifier.padding(SmallPadding)
                    ) {
                        if (menuIcon != null) {
                            menuIcon()
                        } else {
                            Icon(
                                Icons.Rounded.MoreVert,
                                contentDescription = menuIconContentDescription ?: "More options",
                                tint = contentColor
                            )
                        }
                    }

                    val effectiveAnchor = menuAnchorPos ?: barAnchor
                    if (dropdownContent != null) {
                        dropdownContent(isExpanded, onDismissDropdown, effectiveAnchor)
                    } else if (menuItems != null) {
                        XenonDropDown(
                            expanded = isExpanded,
                            onDismissRequest = onDismissDropdown,
                            items = menuItems,
                            hazeState = if (blurEnabled) hazeState else null,
                            anchorPos = effectiveAnchor,
                            offsetY = menuOffsetY,
                            offsetX = menuOffsetX,
                            radius = menuRadius,
                            widthMin = menuWidthMin,
                            widthMax = menuWidthMax,
                            shadowElevation = menuShadowElevation,
                            alignment = menuAlignment,
                            maxLines = menuMaxLines,
                            mainContextFont = menuMainContextFont,
                            containerColor = menuContainerColor,
                            hazeStyle = menuHazeStyle,
                        )
                    }
                }
            } else if (balanceTitle && hasLeadingButton && actions == null) {
                Spacer(modifier = Modifier.size(MinTouchTargetSize))
            }
        }
    }
}

@Composable
fun MorphingBackCloseIcon(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = ExtraLargeSpacing
) {
    Canvas(modifier = modifier.size(size)) {
        val strokeWidth = SmallerStroke.toPx()

        // Segments: 0.0 = Close, 1.0 = Back

        // Segment 1: One line of X -> Top Wing of Back
        drawMorphingLine(
            start0 = Offset(0.2f, 0.2f), end0 = Offset(0.8f, 0.8f),
            start1 = Offset(0.45f, 0.2f), end1 = Offset(0.15f, 0.5f),
            progress = progress,
            color = color,
            strokeWidth = strokeWidth
        )

        // Segment 2: Other line of X -> Bottom Wing of Back
        drawMorphingLine(
            start0 = Offset(0.8f, 0.2f), end0 = Offset(0.2f, 0.8f),
            start1 = Offset(0.45f, 0.8f), end1 = Offset(0.15f, 0.5f),
            progress = progress,
            color = color,
            strokeWidth = strokeWidth
        )

        // Segment 3: Center point -> Shaft of Back
        drawMorphingLine(
            start0 = Offset(0.5f, 0.5f), end0 = Offset(0.5f, 0.5f),
            start1 = Offset(0.15f, 0.5f), end1 = Offset(0.85f, 0.5f),
            progress = progress,
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

private fun DrawScope.drawMorphingLine(
    start0: Offset, end0: Offset,
    start1: Offset, end1: Offset,
    progress: Float,
    color: Color,
    strokeWidth: Float
) {
    val startX = lerp(start0.x, start1.x, progress) * size.width
    val startY = lerp(start0.y, start1.y, progress) * size.height
    val endX = lerp(end0.x, end1.x, progress) * size.width
    val endY = lerp(end0.y, end1.y, progress) * size.height

    drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(endX, endY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}