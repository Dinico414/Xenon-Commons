@file:Suppress("unused", "ConstPropertyName")

package com.xenon.mylibrary.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xenon.mylibrary.ActivityScreen
import com.xenon.mylibrary.DrawerMode
import com.xenon.mylibrary.R
import com.xenon.mylibrary.rememberAdaptiveDrawerState
import com.xenon.mylibrary.res.FloatingToolbarContent
import com.xenon.mylibrary.res.MenuItem
import com.xenon.mylibrary.res.SettingsTile
import com.xenon.mylibrary.res.VerticalFloatingToolbarContent
import com.xenon.mylibrary.res.XenonDrawer
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.theme.XenonTheme
import com.xenon.mylibrary.utils.PermissionItem
import com.xenon.mylibrary.values.LargestPadding
import com.xenon.mylibrary.values.MediumPadding
import dev.chrisbanes.haze.HazeState

object PreviewSizes {
    const val SmallWidth = 320
    const val SmallHeight = 640
    const val CompactWidth = 412
    const val CompactHeight = 915
    const val FoldedWidth = 344
    const val FoldedHeight = 882
    const val UnfoldedWidth = 673
    const val UnfoldedHeight = 841
    const val TabletWidth = 1280
    const val TabletHeight = 800
    const val TabletPortraitWidth = 800
    const val TabletPortraitHeight = 1280
}

internal data class PreviewSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
) : SnackbarVisuals

internal class PreviewSnackbarData(
    override val visuals: SnackbarVisuals,
) : SnackbarData {
    override fun performAction() = Unit

    override fun dismiss() = Unit
}

internal val previewMenuItems = listOf(
    MenuItem(text = "Rename", onClick = {}),
    MenuItem(text = "Duplicate", onClick = {}),
    MenuItem(text = "Delete", onClick = {}),
)

internal val previewPermissions = listOf(
    PermissionItem(
        name = "Notifications",
        description = "Get reminded about the notes you pinned.",
        isGranted = { false },
        request = { },
        guideText = "Notifications are disabled. Enable them in the system settings.",
    ),
    PermissionItem(
        name = "Storage",
        description = "Back up your notes to local storage.",
        isGranted = { false },
        request = { },
    ),
)

@Composable
internal fun PreviewScaffold(
    darkTheme: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    XenonTheme(darkTheme = darkTheme, dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceDim),
            contentAlignment = Alignment.TopCenter,
            content = content,
        )
    }
}

@Composable
internal fun PreviewListContent(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(LargestPadding),
    itemCount: Int = 20,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(MediumPadding),
    ) {
        items(itemCount) { index ->
            SettingsTile(
                title = "Note ${index + 1}",
                subtitle = "Edited ${index + 1}h ago",
                onClick = {},
                icon = { Icon(Icons.Rounded.Description, contentDescription = null) },
            )
        }
    }
}

@Composable
internal fun PreviewFloatingBar(
    listState: LazyListState = rememberLazyListState(),
    isSpannedMode: Boolean = false,
    hingeGap: Dp = 24.dp,
    isFabEnabled: Boolean = true,
    isSearchEnabled: Boolean = true,
) {
    val hazeState = remember { HazeState() }
    var query by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(value = false) }
    var isAddModeActive by remember { mutableStateOf(value = false) }
    var selectedIds by remember { mutableStateOf(emptyList<Int>()) }

    FloatingToolbarContent(
        hazeState = hazeState,
        onSearchQueryChanged = { query = it },
        currentSearchQuery = query,
        lazyListState = listState,
        allowToolbarScrollBehavior = true,
        selectedNoteIds = selectedIds,
        onClearSelection = { selectedIds = emptyList() },
        isAddModeActive = isAddModeActive,
        onAddModeToggle = { isAddModeActive = !isAddModeActive },
        isSearchActive = isSearchActive,
        onIsSearchActiveChange = { isSearchActive = it },
        isSearchEnabled = isSearchEnabled,
        isFabEnabled = isFabEnabled,
        defaultContent = { _, _ ->
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Rounded.Sort, contentDescription = null)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.MoreVert, contentDescription = null)
            }
        },
        isSpannedMode = isSpannedMode,
        spannedModeHingeGap = hingeGap,
    )
}

@Composable
internal fun PreviewVerticalFloatingBar(
    listState: LazyListState = rememberLazyListState(),
    isFabEnabled: Boolean = true,
    isSearchEnabled: Boolean = true,
    docked: Boolean = false,
) {
    val hazeState = remember { HazeState() }
    var query by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(value = false) }
    var isAddModeActive by remember { mutableStateOf(value = false) }
    var selectedIds by remember { mutableStateOf(emptyList<Int>()) }

    VerticalFloatingToolbarContent(
        hazeState = hazeState,
        onSearchQueryChanged = { query = it },
        currentSearchQuery = query,
        lazyListState = listState,
        allowToolbarScrollBehavior = true,
        selectedNoteIds = selectedIds,
        onClearSelection = { selectedIds = emptyList() },
        isAddModeActive = isAddModeActive,
        onAddModeToggle = { isAddModeActive = !isAddModeActive },
        isSearchActive = isSearchActive,
        onIsSearchActiveChange = { isSearchActive = it },
        isSearchEnabled = isSearchEnabled,
        isFabEnabled = isFabEnabled,
        docked = docked,
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

@Composable
internal fun PreviewDrawerContent(
    mode: DrawerMode = DrawerMode.Implemented,
    floating: Boolean = mode == DrawerMode.Overlay,
    isLargeScreenLayout: Boolean = mode == DrawerMode.Implemented,
    collapsable: Boolean = false,
    isCollapsed: Boolean = false,
) {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val isRail = collapsable && isCollapsed
    XenonDrawer(
        title = "Alex Doe",
        profilePictureUrl = null,
        isSignedIn = true,
        noAccIcon = painterResource(R.drawable.ic_default_icon),
        profilePicDesc = "Profile picture",
        floating = floating,
        collapsable = collapsable,
        isCollapsed = isCollapsed,
        isLargeScreenLayout = isLargeScreenLayout,
        drawerState = drawerState,
        hasBottomContent = !isRail,
        bottomContent = if (isRail) {
            null
        } else {
            {
                SettingsTile(
                    title = "Settings",
                    subtitle = "Appearance, backup",
                    onClick = {},
                )
            }
        },
    ) { _ ->
        Column(verticalArrangement = Arrangement.spacedBy(MediumPadding)) {
            if (isRail) {
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Description, contentDescription = "All notes")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Star, contentDescription = "Pinned")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Archive, contentDescription = "Archive")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Delete, contentDescription = "Trash")
                }
            } else {
                SettingsTile(title = "All notes", subtitle = "24", onClick = {})
                SettingsTile(title = "Pinned", subtitle = "3", onClick = {})
                SettingsTile(title = "Archive", onClick = {})
                SettingsTile(title = "Trash", onClick = {})
            }
        }
    }
}

@Composable
internal fun PreviewAdaptiveScreen(
    wideLayout: Boolean,
    drawerInitiallyOpen: Boolean = true,
    title: String = "Library",
) {
    ActivityScreen(
        flexModel = "FlexTopAppBar",
        titleText = title,
        expandedHeight = 180.dp,
        isLargeScreenLayout = wideLayout,
        navigationIcon = { Icon(Icons.Rounded.Menu, contentDescription = "Menu") },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.Star, contentDescription = null)
            }
        },
        drawerState = rememberAdaptiveDrawerState(initiallyOpen = drawerInitiallyOpen),
        content = { PreviewListContent() },
        drawerContent = { mode -> PreviewDrawerContent(mode = mode) },
    )
}

internal fun previewHeader(title: String): @Composable (Float) -> Unit = { fraction ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LargestPadding),
        contentAlignment = Alignment.BottomStart,
    ) {
        Text(
            text = title,
            fontFamily = QuicksandTitleVariable,
            fontSize = (30 - (10 * fraction.coerceIn(0f, 1f))).sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
