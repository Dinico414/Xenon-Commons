package com.xenon.mylibrary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xenon.mylibrary.values.LargestPadding
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlexTopAppBar(
    modifier: Modifier = Modifier,
    collapsedHeight: Dp = 64.dp,
    expandedHeight: Dp = LocalConfiguration.current.screenHeightDp.dp.times(0.3f),
    collapsedByDefault: Boolean = false,
    title: @Composable (fraction: Float) -> Unit = { _ -> },
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    titleAlignment: Alignment = Alignment.CenterStart,
    navigationIconAlignment: Alignment.Vertical = Alignment.Top,
    expandable: Boolean = true,
    expandedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    collapsedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    navigationIconContentColor: Color = MaterialTheme.colorScheme.onSurface,
    actionIconContentColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    val localDensity = LocalDensity.current

    val expandedHeight = remember(expandable) {
        if (expandable) expandedHeight else collapsedHeight
    }

    val fullyCollapsedOffset = remember(collapsedHeight, expandedHeight) {
        with(localDensity) { (collapsedHeight - expandedHeight).toPx() }
    }

    val topAppBarState = rememberTopAppBarState(
        initialHeightOffset = if (collapsedByDefault && expandable) fullyCollapsedOffset else 0f
    )


    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        topAppBarState
    )

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Dummy LargeTopAppBar – consumes the scroll behavior and provides the background/colors
            LargeTopAppBar(
                title = {},
                collapsedHeight = collapsedHeight,
                expandedHeight = expandedHeight,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = expandedContainerColor,
                    scrolledContainerColor = collapsedContainerColor,
                    navigationIconContentColor = navigationIconContentColor,
                    titleContentColor = Color.Transparent,
                    actionIconContentColor = actionIconContentColor
                ),
                scrollBehavior = scrollBehavior
            )

            // fraction: 0f -> fully expanded, 1f -> fully collapsed
            val fraction = if (expandable) scrollBehavior.state.collapsedFraction else 1f
            val curHeight = collapsedHeight.times(fraction) +
                    expandedHeight.times(1 - fraction)
            val offset = curHeight - collapsedHeight

            var boxWidth by remember { mutableIntStateOf(0) }
            val titlePadding = when (titleAlignment) {
                Alignment.CenterStart -> sqrt(fraction) * (boxWidth / localDensity.density) + 8
                else -> 0f
            }

            // Real visible TopAppBar (transparent background, custom content)
            CenterAlignedTopAppBar(
                expandedHeight = curHeight,
                title = {
                    // Navigation icon
                    Box(
                        modifier = Modifier
                            .height(curHeight)
                            .then(
                                when (navigationIconAlignment) {
                                    Alignment.Top -> Modifier.padding(bottom = offset)
                                    Alignment.Bottom -> Modifier.padding(top = offset)
                                    else -> Modifier
                                }
                            )
                            .onGloballyPositioned { coordinates ->
                                if (coordinates.size.width != boxWidth) {
                                    boxWidth = coordinates.size.width
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        navigationIcon()
                    }

                    // Title
                    Box(
                        contentAlignment = titleAlignment,
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                when (titleAlignment) {
                                    Alignment.Center,
                                    Alignment.CenterStart,
                                    Alignment.CenterEnd -> Modifier.height(curHeight)
                                    Alignment.BottomStart,
                                    Alignment.BottomCenter,
                                    Alignment.BottomEnd -> Modifier.padding(top = offset)
                                    else -> Modifier
                                }
                            )
                            .padding(start = titlePadding.dp)
                    ) {
                        Row {
                            title(fraction)
                            Spacer(modifier = Modifier.width(LargestPadding))
                        }
                    }

                    // Actions
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(curHeight)
                            .then(
                                when (navigationIconAlignment) {
                                    Alignment.Top -> Modifier.padding(bottom = offset)
                                    Alignment.Bottom -> Modifier.padding(top = offset)
                                    else -> Modifier
                                }
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Row {
                            actions()
                            Spacer(modifier = Modifier.width(LargestPadding))
                        }
                    }
                },
                navigationIcon = {},
                actions = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = navigationIconContentColor,
                    actionIconContentColor = actionIconContentColor
                )
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}