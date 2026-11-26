@file:Suppress("unused")

package com.xenon.mylibrary.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.xenon.mylibrary.R
import com.xenon.mylibrary.res.AnimatedGradientBackground
import com.xenon.mylibrary.res.GoogleProfilBorder
import com.xenon.mylibrary.res.GoogleProfilePicture
import com.xenon.mylibrary.res.SettingsGoogleTile
import com.xenon.mylibrary.res.SettingsSwitchMenuTile
import com.xenon.mylibrary.res.SettingsSwitchTile
import com.xenon.mylibrary.res.SettingsSwitchTileContext
import com.xenon.mylibrary.res.SettingsTile
import com.xenon.mylibrary.res.SettingsTileContext
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.theme.XenonTheme
import com.xenon.mylibrary.values.ExtraLargeSpacing
import com.xenon.mylibrary.values.LargeIconSize
import com.xenon.mylibrary.values.LargestPadding
import com.xenon.mylibrary.values.MediumPadding

@Preview(name = "SettingsTile", widthDp = 412, heightDp = 320, showBackground = true)
@Composable
private fun SettingsTilePreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            SettingsTile(
                title = "Appearance",
                subtitle = "Light, dark, system",
                onClick = {},
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                showArrow = true,
            )
            SettingsTile(
                title = "Pinned notes",
                subtitle = "3 notes",
                onClick = {},
                icon = { Icon(Icons.Rounded.Star, contentDescription = null) },
                backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        }
    }
}

@Preview(name = "SettingsTileContext", widthDp = 412, heightDp = 360, showBackground = true)
@Composable
private fun SettingsTileContextPreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            SettingsTileContext(
                title = "Backup",
                subtitle = "Last synced 5m ago",
                onClick = {},
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                showArrow = true,
            ) {
                Text(
                    text = "Automatic backup runs every night.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        start = LargestPadding, end = LargestPadding, bottom = LargestPadding,
                    ),
                )
            }
        }
    }
}

@Preview(name = "SettingsSwitchTile", widthDp = 412, heightDp = 360, showBackground = true)
@Composable
private fun SettingsSwitchTilePreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            var checked by remember { mutableStateOf(value = true) }
            SettingsSwitchTile(
                title = "Notifications",
                subtitle = "Daily reminder",
                checked = checked,
                onCheckedChange = { checked = it },
                icon = { Icon(Icons.Rounded.Notifications, contentDescription = null) },
            )
            SettingsSwitchTile(
                title = "Blacked out",
                subtitle = "Pure black surfaces",
                checked = false,
                onCheckedChange = {},
                onClick = {},
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        }
    }
}

@Preview(name = "SettingsSwitchTileContext", widthDp = 412, heightDp = 360, showBackground = true)
@Composable
private fun SettingsSwitchTileContextPreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            SettingsSwitchTileContext(
                title = "Dynamic color",
                subtitle = "Follow the wallpaper",
                checked = true,
                onCheckedChange = {},
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
            ) {
                Text(
                    text = "Colors are taken from your wallpaper.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        start = LargestPadding, end = LargestPadding, bottom = LargestPadding,
                    ),
                )
            }
        }
    }
}

@Preview(name = "SettingsSwitchMenuTile", widthDp = 412, heightDp = 360, showBackground = true)
@Composable
private fun SettingsSwitchMenuTilePreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            SettingsSwitchMenuTile(
                title = "At a glance",
                subtitle = "Show notes on the lock screen",
                checked = true,
                onCheckedChange = {},
                onClick = {},
                icon = { Icon(Icons.Rounded.Notifications, contentDescription = null) },
            )
        }
    }
}

@Preview(name = "SettingsGoogleTile", widthDp = 412, heightDp = 360, showBackground = true)
@Composable
private fun SettingsGoogleTilePreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(MediumPadding),
        ) {
            SettingsGoogleTile(
                title = "Alex Doe",
                subtitle = "alex@example.com",
                isSignedIn = true,
                onClick = {},
                noAccIcon = painterResource(R.drawable.ic_default_icon),
            )
            SettingsGoogleTile(
                title = "Sign in",
                subtitle = "Back up your notes",
                isSignedIn = false,
                onClick = {},
                noAccIcon = painterResource(R.drawable.ic_default_icon),
            )
        }
    }
}

@Preview(name = "Profile", widthDp = 412, heightDp = 240, showBackground = true)
@Composable
private fun GoogleProfilePreview() {
    PreviewScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LargestPadding),
            verticalArrangement = Arrangement.spacedBy(ExtraLargeSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(contentAlignment = Alignment.Center) {
                GoogleProfilBorder(
                    isSignedIn = true,
                    modifier = Modifier.size(LargeIconSize),
                )
                GoogleProfilePicture(
                    profilePictureUrl = null,
                    noAccIcon = painterResource(R.drawable.ic_default_icon),
                    contentDescription = "Profile picture",
                )
            }
            Box(contentAlignment = Alignment.Center) {
                GoogleProfilBorder(
                    isSignedIn = false,
                    modifier = Modifier.size(LargeIconSize),
                )
                GoogleProfilePicture(
                    profilePictureUrl = null,
                    noAccIcon = painterResource(R.drawable.ic_default_icon),
                    contentDescription = "Signed out",
                )
            }
        }
    }
}

@Preview(name = "Gradient background", widthDp = 412, heightDp = 720, showBackground = true)
@Composable
private fun AnimatedGradientBackgroundPreview() {
    XenonTheme(darkTheme = false, dynamicColor = false) {
        AnimatedGradientBackground(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Xenon",
                    fontFamily = QuicksandTitleVariable,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
