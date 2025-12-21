package com.xenon.mylibrary

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TopContainer(
    modifier: Modifier = Modifier,
    collapsedHeight: Dp = 0.dp,
    expandedHeight: Dp = LocalConfiguration.current.screenHeightDp.dp * 0.3f,
    expand: Boolean = false,
    expandedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    collapsedContainerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    headerContent: @Composable (fraction: Float) -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    val animatedHeight by animateDpAsState(
        targetValue = if (expand) expandedHeight else collapsedHeight,
        animationSpec = tween(durationMillis = 400),
        label = "headerHeight"
    )

    val animatedContainerColor by animateColorAsState(
        targetValue = if (expand) expandedContainerColor else collapsedContainerColor,
        animationSpec = tween(durationMillis = 400),
        label = "containerColor"
    )

    val fraction = if (expandedHeight == collapsedHeight) {
        1f
    } else {
        ((animatedHeight - collapsedHeight) / (expandedHeight - collapsedHeight)).coerceIn(0f, 1f)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = animatedContainerColor,
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            WindowInsets.safeDrawing
                                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                                .asPaddingValues()
                        )
                        .height(animatedHeight)

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