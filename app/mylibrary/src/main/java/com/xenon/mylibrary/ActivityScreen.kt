package com.xenon.mylibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.ExtraBigCornerRadius
import com.xenon.mylibrary.values.FullCornerRadius
import com.xenon.mylibrary.values.HugeBiggerSpacing
import com.xenon.mylibrary.values.HugerSpacing
import com.xenon.mylibrary.values.SmallButtonSize
import com.xenon.mylibrary.values.SmallPadding
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.ui.util.lerp as floatLerp

@Suppress("unused")
@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    flexModel: String? = "FlexTopAppBar",
    titleText: String = "",
    hasNavigationIconExtraContent: Boolean = false,
    navigationIcon: (@Composable () -> Unit)? = null,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIconExtraContent: @Composable RowScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedAppBarTextColor: Color = MaterialTheme.colorScheme.onSurface,
    expandedAppBarTextColor: Color = MaterialTheme.colorScheme.primary,
    appBarNavigationIconContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    appBarActionIconContentColor: Color = MaterialTheme.colorScheme.onSurface,
    screenBackgroundColor: Color = MaterialTheme.colorScheme.surfaceDim,
    contentBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    expandedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    collapsedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    contentCornerRadius: Dp = ExtraBigCornerRadius,
    navigationIconStartPadding: Dp = SmallPadding,
    navigationIconPadding: Dp = SmallPadding,
    navigationIconSpacing: Dp = SmallPadding,
    collapsedByDefault: Boolean = false,
    collapsedHeight: Dp = HugerSpacing,
    expandedHeight: Dp = LocalConfiguration.current.screenHeightDp.dp.times(0.3f),
    expandable: Boolean = true,
    expand: Boolean = false,
    headerContent: @Composable (fraction: Float) -> Unit = {},
    isLargeScreenLayout: Boolean = false,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
    drawerState: AdaptiveDrawerState = rememberAdaptiveDrawerState(),
    drawerMode: DrawerMode? = null,
    drawerWidth: Dp = 360.dp,
    isSpannedMode: Boolean? = null,
    spannedHingeGap: Dp? = null,
    drawerContent: (@Composable (mode: DrawerMode) -> Unit)? = null,
    rightSliverContent: (@Composable () -> Unit)? = null,
    rightSliverWidth: Dp = 80.dp,
    content: @Composable (PaddingValues) -> Unit,
    dialogs: @Composable () -> Unit = {},
) {
    val deviceConfig = com.xenon.mylibrary.theme.LocalDeviceConfig.current
    val activeSpanned = isSpannedMode ?: deviceConfig.isSpannedMode
    val activeHingeGap = spannedHingeGap ?: deviceConfig.hingeGapDp

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = activeSpanned || isLargeScreenLayout || (maxWidth >= DrawerDockedBreakpoint)
        val effectiveRightSliver = if (isWideScreen) rightSliverContent else null
        val scope = rememberCoroutineScope()
        val onNavigationClick: (() -> Unit)? = onNavigationIconClick
            ?: drawerContent?.let { { scope.launch { drawerState.toggleAdaptive(isWideScreen) } } }

        val navigationIconSlot = @Composable {
            val interactionSource = remember { MutableInteractionSource() }
            val boxModifier = Modifier
                .defaultMinSize(minWidth = SmallButtonSize)
                .clip(RoundedCornerShape(FullCornerRadius))
                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                .then(
                    if (onNavigationClick != null) {
                        Modifier.clickable(
                            onClick = onNavigationClick,
                            role = Role.Button,
                            interactionSource = interactionSource,
                            indication = ripple(bounded = true),
                        )
                    } else {
                        Modifier
                    }
                )

            Box(modifier = Modifier.width(HugeBiggerSpacing), contentAlignment = Alignment.Center) {
                Box(modifier = boxModifier, contentAlignment = Alignment.Center) {
                    CompositionLocalProvider(LocalContentColor provides appBarNavigationIconContentColor) {
                        Row(
                            modifier = Modifier
                                .padding(start = navigationIconStartPadding, end = navigationIconPadding)
                                .padding(vertical = navigationIconPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(navigationIconSpacing),
                        ) {
                            if (navigationIcon != null) {
                                navigationIcon()
                            } else if (onNavigationClick != null) {
                                Icon(
                                    imageVector = Icons.Rounded.Menu,
                                    contentDescription = "Navigation drawer",
                                    tint = appBarNavigationIconContentColor,
                                )
                            }
                            if (hasNavigationIconExtraContent) {
                                navigationIconExtraContent()
                            }
                        }
                    }
                }
            }
        }

        val screen: @Composable () -> Unit = {
            when (flexModel) {
                "FlexTopAppBar" -> FlexTopAppBar(
                    collapsedHeight = collapsedHeight,
                    expandedHeight = expandedHeight,
                    collapsedByDefault = collapsedByDefault,
                    expandable = expandable,
                    title = { fraction ->
                        Text(
                            text = titleText,
                            fontFamily = mainContextFont,
                            color = lerp(expandedAppBarTextColor, collapsedAppBarTextColor, fraction),
                            fontSize = floatLerp(32F, 22F, fraction).sp,
                        fontWeight = FontWeight(floatLerp(700F, 100F, fraction).roundToInt()),
                        )
                    },
                    navigationIcon = navigationIconSlot,
                    actions = actions,
                    titleAlignment = if (onNavigationClick == null && !hasNavigationIconExtraContent) {
                        Alignment.Center
                    } else {
                        Alignment.CenterStart
                    },
                    collapsedContainerColor = screenBackgroundColor,
                    expandedContainerColor = screenBackgroundColor,
                    navigationIconContentColor = appBarNavigationIconContentColor,
                    actionIconContentColor = appBarActionIconContentColor,
                    mainContextFont = mainContextFont,
                    subContextFont = subContextFont,
                    modifier = modifier,
                ) { padding ->
                    SharedScreenContent(
                        modifier = contentModifier,
                        paddingValuesFromAppBar = padding,
                        screenBackgroundColor = screenBackgroundColor,
                        contentBackgroundColor = contentBackgroundColor,
                        contentCornerRadius = contentCornerRadius,
                        isLargeScreenLayout = isLargeScreenLayout,
                        rightSliverContent = effectiveRightSliver,
                        rightSliverWidth = rightSliverWidth,
                        content = content,
                        dialogs = dialogs,
                    )
                }

                "FlexTopContainer" -> FlexTopContainer(
                    modifier = modifier,
                    collapsedHeight = collapsedHeight,
                    expandedHeight = expandedHeight,
                    collapsedByDefault = collapsedByDefault,
                    expandable = expandable,
                    expandedContainerColor = screenBackgroundColor,
                    collapsedContainerColor = screenBackgroundColor,
                    headerContent = headerContent,
                ) { padding ->
                    SharedScreenContent(
                        modifier = contentModifier,
                        paddingValuesFromAppBar = padding,
                        screenBackgroundColor = screenBackgroundColor,
                        contentBackgroundColor = contentBackgroundColor,
                        contentCornerRadius = contentCornerRadius,
                        isLargeScreenLayout = isLargeScreenLayout,
                        rightSliverContent = effectiveRightSliver,
                        rightSliverWidth = rightSliverWidth,
                        content = content,
                        dialogs = dialogs,
                    )
                }

                "TopContainer" -> TopContainer(
                    modifier = modifier,
                    collapsedHeight = collapsedHeight,
                    expandedHeight = expandedHeight,
                    expand = expand,
                    expandedContainerColor = expandedContainerColor,
                    collapsedContainerColor = collapsedContainerColor,
                    headerContent = headerContent,
                ) { padding ->
                    SharedScreenContent(
                        modifier = contentModifier,
                        paddingValuesFromAppBar = padding,
                        screenBackgroundColor = screenBackgroundColor,
                        contentBackgroundColor = contentBackgroundColor,
                        contentCornerRadius = contentCornerRadius,
                        isLargeScreenLayout = isLargeScreenLayout,
                        rightSliverContent = rightSliverContent,
                        rightSliverWidth = rightSliverWidth,
                        content = content,
                        dialogs = dialogs,
                    )
                }
            }
        }

        if (drawerContent == null) {
            screen()
        } else {
            AdaptiveDrawerLayout(
                mode = drawerMode,
                state = drawerState,
                drawerWidth = drawerWidth,
                isSpannedMode = activeSpanned,
                spannedHingeGap = activeHingeGap,
                drawerContent = drawerContent,
                content = screen,
            )
        }
    }
}

@Composable
private fun SharedScreenContent(
    modifier: Modifier,
    paddingValuesFromAppBar: PaddingValues,
    screenBackgroundColor: Color,
    contentBackgroundColor: Color,
    contentCornerRadius: Dp,
    isLargeScreenLayout: Boolean,
    rightSliverContent: (@Composable () -> Unit)?,
    rightSliverWidth: Dp,
    content: @Composable (PaddingValues) -> Unit,
    dialogs: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(top = paddingValuesFromAppBar.calculateTopPadding())
                .padding(
                    if (isLargeScreenLayout) {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.End).asPaddingValues()
                    } else {
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal).asPaddingValues()
                    }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier)
                    .clip(RoundedCornerShape(topStart = contentCornerRadius, topEnd = contentCornerRadius))
                    .background(contentBackgroundColor)
            ) {
                content(paddingValuesFromAppBar)
            }
        }

        if (rightSliverContent != null) {
            Box(
                modifier = Modifier
                    .width(rightSliverWidth)
                    .fillMaxHeight()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.End + WindowInsetsSides.Vertical
                        )
                    )
            ) {
                rightSliverContent()
            }
        }
    }
    dialogs()
}
