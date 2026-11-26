@file:Suppress("unused")

package com.xenon.mylibrary.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.xenon.mylibrary.res.DialogClearDataConfirmation
import com.xenon.mylibrary.res.DialogCoverDisplaySelection
import com.xenon.mylibrary.res.DialogLanguageSelection
import com.xenon.mylibrary.res.DialogResetSettingsConfirmation
import com.xenon.mylibrary.res.DialogSignOut
import com.xenon.mylibrary.res.DialogThemeSelection
import com.xenon.mylibrary.res.DialogVersionNumber
import com.xenon.mylibrary.res.LanguageOption
import com.xenon.mylibrary.res.PermissionScreen
import com.xenon.mylibrary.res.ThemeSetting
import com.xenon.mylibrary.res.WelcomeScreen

private val previewLanguages = listOf(
    LanguageOption(displayName = "English", localeTag = "en"),
    LanguageOption(displayName = "Deutsch", localeTag = "de"),
    LanguageOption(displayName = "Français", localeTag = "fr"),
)

@Preview(name = "DialogSignOut", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogSignOutPreview() {
    PreviewScaffold {
        DialogSignOut(
            onConfirm = {},
            onDismiss = {},
            dialogTitle = "Sign out?",
            confirmText = "Sign out",
            descriptionText = "Your notes stay on this device.",
        )
    }
}

@Preview(name = "DialogClearDataConfirmation", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogClearDataConfirmationPreview() {
    PreviewScaffold {
        DialogClearDataConfirmation(
            onConfirm = {},
            onDismiss = {},
            dialogTitle = "Clear all data?",
            confirmText = "Clear data",
            descriptionText = "Every note, reminder and setting will be deleted.",
        )
    }
}

@Preview(name = "DialogResetSettingsConfirmation", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogResetSettingsConfirmationPreview() {
    PreviewScaffold {
        DialogResetSettingsConfirmation(
            onConfirm = {},
            onDismiss = {},
            dialogTitle = "Reset settings?",
            confirmText = "Reset",
            descriptionText = "All preferences go back to their defaults.",
        )
    }
}

@Preview(name = "DialogVersionNumber", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogVersionNumberPreview() {
    PreviewScaffold {
        DialogVersionNumber(
            onDismiss = {},
            dialogTitle = "Version",
            confirmText = "More info",
            appString = "Xenon Notes",
            appVersion = "3.1.7",
            xenonUiString = "Xenon UI",
            xenonUIVersion = "1.4.0",
            xenonCommonsString = "Xenon Commons",
            xenonCommonsVersion = "3.1.7",
        )
    }
}

@Preview(name = "DialogCoverDisplaySelection", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogCoverDisplaySelectionPreview() {
    PreviewScaffold {
        DialogCoverDisplaySelection(
            onConfirm = {},
            onDismiss = {},
            dialogTitle = "Cover display",
            confirmText = "Enable",
            action2Text = "Not now",
            descriptionText = "Show a compact version of Xenon on the cover display.",
        )
    }
}

@Preview(name = "DialogThemeSelection", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogThemeSelectionPreview() {
    PreviewScaffold {
        DialogThemeSelection(
            themeOptions = arrayOf(ThemeSetting.LIGHT, ThemeSetting.DARK, ThemeSetting.SYSTEM),
            currentThemeIndex = 2,
            onThemeSelected = {},
            onDismiss = {},
            onConfirm = {},
            dialogTitle = "Theme",
            confirmText = "Apply",
        )
    }
}

@Preview(name = "DialogLanguageSelection", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun DialogLanguageSelectionPreview() {
    PreviewScaffold {
        DialogLanguageSelection(
            availableLanguages = previewLanguages,
            currentLanguageTag = "en",
            onLanguageSelected = {},
            onDismiss = {},
            onConfirm = {},
            dialogTitle = "Language",
            confirmText = "Apply",
        )
    }
}

@Preview(name = "WelcomeScreen", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun WelcomeScreenPreview() {
    PreviewScaffold {
        WelcomeScreen(
            title = "Welcome to Xenon",
            description = "A quiet, private place for everything you want to remember.",
            initialCountdown = 3,
        ) {}
    }
}

@Preview(name = "PermissionScreen", widthDp = PreviewSizes.CompactWidth, heightDp = PreviewSizes.CompactHeight)
@Composable
private fun PermissionScreenPreview() {
    PreviewScaffold {
        PermissionScreen(
            permissions = previewPermissions,
            isFirstLaunch = true,
        ) {}
    }
}
