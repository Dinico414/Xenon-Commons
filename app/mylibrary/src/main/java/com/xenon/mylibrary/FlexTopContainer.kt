package com.xenon.mylibrary

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlexTopContainer(
    modifier: Modifier = Modifier,
    collapsedHeight: Dp = 64.dp,
    expandedHeight: Dp = LocalConfiguration.current.screenHeightDp.dp.times(0.3f),
    expandable: Boolean = true,
    expandedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    collapsedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    headerContent: @Composable (fraction: Float) -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    val effectiveExpandedHeight = if (expandable) expandedHeight else collapsedHeight

    val scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val fraction = if (expandable) scrollBehavior.state.collapsedFraction else 1f

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { },
                navigationIcon = { },
                actions = { },
                collapsedHeight = collapsedHeight,
                expandedHeight = effectiveExpandedHeight,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = expandedContainerColor,
                    scrolledContainerColor = collapsedContainerColor
                ),
                scrollBehavior = scrollBehavior
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = if (fraction < 1f) expandedContainerColor else collapsedContainerColor
            ) {
                val currentHeight = collapsedHeight * fraction + effectiveExpandedHeight * (1f - fraction)

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.height(currentHeight)
                ) {
                    headerContent(fraction)
                }
            }
        },
        content = { innerPadding ->
            content(innerPadding)
        }
    )
}