package com.xenon.mylibrary.res

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.DialogProperties
import com.xenon.mylibrary.theme.QuicksandTitleVariable

@Suppress("unused")
@Composable
fun DialogResetSettingsConfirmation(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dialogTitle: String,
    confirmText: String,
    descriptionText: String,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
) {
    val textColor = MaterialTheme.colorScheme.onErrorContainer

    XenonDialog(
        onDismissRequest = onDismiss,
        title = dialogTitle,
        containerColor = MaterialTheme.colorScheme.errorContainer,
        dismissIconButtonContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
        dismissIconButtonContentColor = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f),
        confirmContainerColor = MaterialTheme.colorScheme.error,
        confirmContentColor = MaterialTheme.colorScheme.onError,
        confirmButtonText = confirmText,
        onConfirmButtonClick = { onConfirm() },
        mainContextFont = mainContextFont,
        subContextFont = subContextFont,
        properties = DialogProperties(usePlatformDefaultWidth = true),
        contentManagesScrolling = false,
    ) {
        Text(
            text = descriptionText,
            fontFamily = subContextFont,
            color = textColor,
        )
    }
}
