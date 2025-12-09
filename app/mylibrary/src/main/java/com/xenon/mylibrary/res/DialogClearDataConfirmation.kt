package com.xenon.mylibrary.res

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties


@Composable
fun DialogClearDataConfirmation(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dialogTitle: String,
    confirmText: String,
    descriptionText: String
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
        properties = DialogProperties(usePlatformDefaultWidth = true),
        contentManagesScrolling = false,
    ) {
        Text(
            text = descriptionText,
            color = textColor)
    }
}