package com.xenon.mylibrary.res

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
// REMOVE: import androidx.compose.ui.res.stringResource // No longer needed
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogSignOut(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    titleText: String,
    confirmButtonText: String,
    descriptionText: String
) {
    XenonDialog(
        onDismissRequest = onDismiss,
        title = titleText,
        confirmButtonText = confirmButtonText,
        onConfirmButtonClick = { onConfirm() },
        properties = DialogProperties(usePlatformDefaultWidth = true),
        contentManagesScrolling = false,
    ) {
        Text(
            text = descriptionText,
        )
    }
}