@file:Suppress("unused")

package com.xenon.mylibrary.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xenon.mylibrary.ActivityScreen
import com.xenon.mylibrary.DrawerMode
import com.xenon.mylibrary.FlexTopAppBar
import com.xenon.mylibrary.FlexTopContainer
import com.xenon.mylibrary.TopContainer
import com.xenon.mylibrary.rememberAdaptiveDrawerState
import com.xenon.mylibrary.res.VerticalFloatingToolbarContent
import dev.chrisbanes.haze.HazeState

private val previewActions: @Composable RowScope.() -> Unit = {
    IconButton(onClick = {}) {
        Icon(Icons.Rounded.Settings, contentDescription = null)
    }
    IconButton(onClick = {}) {
        Icon(Icons.Rounded.MoreVert, contentDescription = null)
    }
}

private val previewNavIcon: @Composable () -> Unit = {
    Icon(Icons.Rounded.Menu, contentDescription = "Menu")
}

@Preview(name = "AppBar · small", widthDp = PreviewSizes.SmallWidth, heightDp = PreviewSizes.SmallHeight)
@Composable
private fun FlexTopAppBarSmallPreview() {
    PreviewScaffold {
        FlexTopAppBar(
            collapsedHeight = 64.dp,
            expandedHeight = 160.dp,
            title = previewHeader("Notes"),
            navigationIcon = previewNavIcon,
            actions = previewActions,
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "AppBar · compact", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun FlexTopAppBarCompactPreview() {
    PreviewScaffold {
        FlexTopAppBar(
            collapsedHeight = 64.dp,
            expandedHeight = 240.dp,
            title = previewHeader("All notes"),
            navigationIcon = previewNavIcon,
            actions = previewActions,
            titleAlignment = Alignment.CenterStart,
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "AppBar · foldable unfolded", widthDp = PreviewSizes.UnfoldedWidth, heightDp = PreviewSizes.UnfoldedHeight)
@Composable
private fun FlexTopAppBarFoldablePreview() {
    PreviewScaffold(darkTheme = true) {
        FlexTopAppBar(
            collapsedHeight = 64.dp,
            expandedHeight = 200.dp,
            title = previewHeader("Notes"),
            navigationIcon = previewNavIcon,
            actions = previewActions,
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "AppBar · tablet", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun FlexTopAppBarTabletPreview() {
    PreviewScaffold {
        FlexTopAppBar(
            collapsedHeight = 72.dp,
            expandedHeight = 300.dp,
            title = previewHeader("Library"),
            navigationIcon = previewNavIcon,
            actions = previewActions,
            navigationIconAlignment = Alignment.Bottom,
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "FlexTopContainer · small", widthDp = PreviewSizes.SmallWidth, heightDp = PreviewSizes.SmallHeight)
@Composable
private fun FlexTopContainerSmallPreview() {
    PreviewScaffold {
        FlexTopContainer(
            collapsedHeight = 64.dp,
            expandedHeight = 160.dp,
            headerContent = previewHeader("Inbox"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "FlexTopContainer · compact", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun FlexTopContainerCompactPreview() {
    PreviewScaffold {
        FlexTopContainer(
            collapsedHeight = 64.dp,
            expandedHeight = 240.dp,
            expandable = false,
            headerContent = previewHeader("Inbox"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "FlexTopContainer · foldable", widthDp = PreviewSizes.FoldedWidth, heightDp = PreviewSizes.FoldedHeight)
@Composable
private fun FlexTopContainerFoldablePreview() {
    PreviewScaffold {
        FlexTopContainer(
            collapsedHeight = 64.dp,
            expandedHeight = 220.dp,
            headerContent = previewHeader("Notes"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "FlexTopContainer · tablet", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun FlexTopContainerTabletPreview() {
    PreviewScaffold(darkTheme = true) {
        FlexTopContainer(
            collapsedHeight = 72.dp,
            expandedHeight = 280.dp,
            headerContent = previewHeader("Collections"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "TopContainer · compact", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun TopContainerCompactPreview() {
    PreviewScaffold {
        TopContainer(
            collapsedHeight = 64.dp,
            expandedHeight = 220.dp,
            expand = true,
            headerContent = previewHeader("Home"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "TopContainer · collapsed", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun TopContainerCollapsedPreview() {
    PreviewScaffold {
        TopContainer(
            collapsedHeight = 64.dp,
            expandedHeight = 220.dp,
            expand = false,
            headerContent = previewHeader("Home"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "TopContainer · tablet portrait", widthDp = PreviewSizes.TabletPortraitWidth, heightDp = PreviewSizes.TabletPortraitHeight)
@Composable
private fun TopContainerTabletPreview() {
    PreviewScaffold {
        TopContainer(
            collapsedHeight = 72.dp,
            expandedHeight = 220.dp,
            expand = true,
            headerContent = previewHeader("Dashboard"),
        ) { padding ->
            PreviewListContent(modifier = Modifier.padding(padding))
        }
    }
}

@Preview(name = "ActivityScreen · AppBar small", widthDp = PreviewSizes.SmallWidth, heightDp = PreviewSizes.SmallHeight)
@Composable
private fun ActivityScreenAppBarSmallPreview() {
    PreviewScaffold {
        ActivityScreen(
            flexModel = "FlexTopAppBar",
            titleText = "Notes",
            navigationIcon = { Icon(Icons.Rounded.Menu, contentDescription = "Menu") },
            onNavigationIconClick = {},
            actions = previewActions,
            expandedHeight = 160.dp,
            content = {
                PreviewListContent()
            },
        )
    }
}

@Preview(name = "ActivityScreen · AppBar compact", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun ActivityScreenAppBarCompactPreview() {
    PreviewScaffold {
        ActivityScreen(
            flexModel = "FlexTopAppBar",
            titleText = "All notes",
            navigationIcon = { Icon(Icons.Rounded.Menu, contentDescription = "Menu") },
            onNavigationIconClick = {},
            actions = previewActions,
            expandedHeight = 240.dp,
            content = {
                PreviewListContent()
            },
        )
    }
}

@Preview(name = "ActivityScreen · FlexTopContainer foldable", widthDp = PreviewSizes.FoldedWidth, heightDp = PreviewSizes.FoldedHeight)
@Composable
private fun ActivityScreenFlexContainerFoldablePreview() {
    PreviewScaffold {
        ActivityScreen(
            flexModel = "FlexTopContainer",
            expandedHeight = 220.dp,
            headerContent = previewHeader("Inbox"),
            content = {
                PreviewListContent()
            },
        )
    }
}

@Preview(name = "ActivityScreen · TopContainer tablet", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun ActivityScreenTopContainerTabletPreview() {
    PreviewScaffold {
        ActivityScreen(
            flexModel = "TopContainer",
            expandedHeight = 180.dp,
            expand = true,
            isLargeScreenLayout = true,
            headerContent = previewHeader("Workspace"),
            content = {
                PreviewListContent()
            },
        )
    }
}

@Preview(name = "Drawer · overlay", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun XenonDrawerFloatingPreview() {
    PreviewScaffold {
        PreviewDrawerContent(mode = DrawerMode.Overlay)
    }
}

@Preview(name = "Drawer · implemented", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun XenonDrawerPlainPreview() {
    PreviewScaffold(darkTheme = true) {
        PreviewDrawerContent(mode = DrawerMode.Implemented)
    }
}

@Preview(name = "Drawer · rail", widthDp = 120, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun XenonDrawerRailPreview() {
    PreviewScaffold {
        PreviewDrawerContent(mode = DrawerMode.Implemented, collapsable = true, isCollapsed = true)
    }
}

@Preview(name = "Screen · phone · drawer closed", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun ScreenPhoneDrawerClosedPreview() {
    PreviewScaffold {
        PreviewAdaptiveScreen(wideLayout = false, drawerInitiallyOpen = false)
    }
}

@Preview(name = "Screen · phone · drawer overlay", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun ScreenPhoneDrawerOpenPreview() {
    PreviewScaffold {
        PreviewAdaptiveScreen(wideLayout = false)
    }
}

@Preview(name = "Screen · foldable · drawer implemented", widthDp = PreviewSizes.UnfoldedWidth, heightDp = PreviewSizes.UnfoldedHeight)
@Composable
private fun ScreenFoldableDockedPreview() {
    PreviewScaffold {
        PreviewAdaptiveScreen(wideLayout = true)
    }
}

@Preview(name = "Screen · Surface Duo · 50/50 spanned", widthDp = 800, heightDp = 600)
@Composable
private fun ScreenSurfaceDuoSpannedPreview() {
    PreviewScaffold {
        ActivityScreen(
            flexModel = "FlexTopAppBar",
            titleText = "Duo Spanned",
            expandedHeight = 160.dp,
            isSpannedMode = true,
            spannedHingeGap = 30.dp,
            drawerState = rememberAdaptiveDrawerState(initialMode = DrawerMode.Implemented, initiallyOpen = true),
            drawerContent = { mode -> PreviewDrawerContent(mode = mode) },
            content = { PreviewListContent() },
        )
    }
}

@Preview(name = "Screen · tablet · drawer implemented", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun ScreenTabletDockedPreview() {
    PreviewScaffold(darkTheme = true) {
        PreviewAdaptiveScreen(wideLayout = true)
    }
}

@Preview(name = "Screen · tablet · FlexTopContainer", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun XenonDrawerCollapsedPreview() {
    PreviewScaffold {
        ActivityScreen(
            flexModel = "FlexTopContainer",
            expandedHeight = 180.dp,
            isLargeScreenLayout = true,
            headerContent = previewHeader("Library"),
            drawerState = rememberAdaptiveDrawerState(initiallyOpen = true),
            drawerContent = { mode -> PreviewDrawerContent(mode = mode) },
            content = { PreviewListContent() },
        )
    }
}

@Preview(name = "Screen · tablet · drawer + floating bar", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun XenonDrawerExpandedRailPreview() {
    PreviewScaffold(darkTheme = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            ActivityScreen(
                flexModel = "FlexTopAppBar",
                titleText = "Library",
                expandedHeight = 180.dp,
                isLargeScreenLayout = true,
                actions = previewActions,
                drawerState = rememberAdaptiveDrawerState(initiallyOpen = true),
                drawerContent = { mode -> PreviewDrawerContent(mode = mode) },
                contentModifier = Modifier.padding(bottom = 200.dp),
                content = { PreviewListContent() },
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Box(modifier = Modifier.padding(bottom = 120.dp)) {
                    PreviewFloatingBar()
                }
            }
        }
    }
}

@Preview(name = "FloatingBar · compact", widthDp = PreviewSizes.CompactWidth, heightDp = 200)
@Composable
private fun FloatingToolbarCompactPreview() {
    PreviewScaffold {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PreviewFloatingBar(listState = rememberLazyListState())
        }
    }
}

@Preview(name = "FloatingBar · no fab", widthDp = PreviewSizes.CompactWidth, heightDp = 200)
@Composable
private fun FloatingToolbarNoFabPreview() {
    PreviewScaffold {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PreviewFloatingBar(isFabEnabled = false)
        }
    }
}

@Preview(name = "FloatingBar · no search · no fab", widthDp = PreviewSizes.SmallWidth, heightDp = 200)
@Composable
private fun FloatingToolbarMinimalPreview() {
    PreviewScaffold {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PreviewFloatingBar(isSearchEnabled = false, isFabEnabled = false)
        }
    }
}

@Preview(name = "FloatingBar · spanned foldable", widthDp = PreviewSizes.UnfoldedWidth, heightDp = 200)
@Composable
private fun FloatingToolbarSpannedPreview() {
    PreviewScaffold {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PreviewFloatingBar(isSpannedMode = true, hingeGap = 64.dp)
        }
    }
}

@Preview(name = "Screen · expanded · drawer + floating bar", widthDp = 900, heightDp = 800)
@Composable
private fun ScreenWithDrawerAndBarPreview() {
    PreviewScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            ActivityScreen(
                flexModel = "FlexTopAppBar",
                titleText = "Notes",
                actions = previewActions,
                expandedHeight = 220.dp,
                drawerState = rememberAdaptiveDrawerState(initiallyOpen = true),
                drawerContent = { mode -> PreviewDrawerContent(mode = mode) },
                contentModifier = Modifier.padding(bottom = 200.dp),
                content = { PreviewListContent() },
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Box(modifier = Modifier.padding(bottom = 120.dp)) {
                    PreviewFloatingBar()
                }
            }
        }
    }
}

@Preview(name = "VerticalFloatingBar · floating", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun VerticalFloatingToolbarPreview() {
    PreviewScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            PreviewListContent()
            val hazeState = remember { HazeState() }
            var query by remember { mutableStateOf("") }
            var isSearchActive by remember { mutableStateOf(false) }
            var isAddModeActive by remember { mutableStateOf(false) }
            VerticalFloatingToolbarContent(
                hazeState = hazeState,
                onSearchQueryChanged = { query = it },
                currentSearchQuery = query,
                lazyListState = rememberLazyListState(),
                allowToolbarScrollBehavior = true,
                selectedNoteIds = emptyList(),
                onClearSelection = {},
                isAddModeActive = isAddModeActive,
                onAddModeToggle = { isAddModeActive = !isAddModeActive },
                isSearchActive = isSearchActive,
                onIsSearchActiveChange = { isSearchActive = it },
                defaultContent = { _, _ ->
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Rounded.Sort, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.MoreVert, contentDescription = null)
                    }
                },
            )
        }
    }
}

@Preview(name = "ActivityScreen · right sliver docked", widthDp = PreviewSizes.TabletWidth, heightDp = PreviewSizes.TabletHeight)
@Composable
private fun ActivityScreenRightSliverPreview() {
    PreviewScaffold {
        val hazeState = remember { HazeState() }
        var query by remember { mutableStateOf("") }
        var isSearchActive by remember { mutableStateOf(false) }
        var isAddModeActive by remember { mutableStateOf(false) }
        ActivityScreen(
            flexModel = "FlexTopAppBar",
            titleText = "Notes & Rail",
            expandedHeight = 180.dp,
            isLargeScreenLayout = true,
            rightSliverWidth = 80.dp,
            rightSliverContent = {
                VerticalFloatingToolbarContent(
                    hazeState = hazeState,
                    onSearchQueryChanged = { query = it },
                    currentSearchQuery = query,
                    lazyListState = rememberLazyListState(),
                    allowToolbarScrollBehavior = true,
                    selectedNoteIds = emptyList(),
                    onClearSelection = {},
                    isAddModeActive = isAddModeActive,
                    onAddModeToggle = { isAddModeActive = !isAddModeActive },
                    isSearchActive = isSearchActive,
                    onIsSearchActiveChange = { isSearchActive = it },
                    docked = true,
                    defaultContent = { _, _ ->
                        IconButton(onClick = {}) {
                            Icon(Icons.AutoMirrored.Rounded.Sort, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Rounded.MoreVert, contentDescription = null)
                        }
                    },
                )
            },
            content = { PreviewListContent() },
        )
    }
}
