package com.xenon.mylibrary.res

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.DialogProperties
import com.xenon.mylibrary.theme.QuicksandTitleVariable

@Suppress("unused")
@Composable
fun DialogSignOut(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dialogTitle: String,
    confirmText: String,
    descriptionText: String,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
) {
    XenonDialog(
        onDismissRequest = onDismiss,
        title = dialogTitle,
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
        )
    }
}
