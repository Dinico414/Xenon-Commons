package com.xenon.mylibrary.res

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
// REMOVE: import androidx.compose.ui.res.stringResource // No longer needed
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogCoverDisplaySelection(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    titleText: String,
    confirmButtonText: String,
    actionButton2Text: String,
    descriptionText: String
) {
    XenonDialog(
        onDismissRequest = onDismiss,
        title = titleText,
        confirmButtonText = confirmButtonText,
        onConfirmButtonClick = { onConfirm() },
        actionButton2Text = actionButton2Text,
        onActionButton2Click = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = true),
        contentManagesScrolling = false,
    ) {
        val containerSize = LocalWindowInfo.current.containerSize
        Column {
            Text(
                text = descriptionText,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Screen size: ${containerSize.width} x ${containerSize.height} px",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}