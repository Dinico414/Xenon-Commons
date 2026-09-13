@file:Suppress("unused")

package com.xenon.mylibrary.res

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.DialogProperties
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.MediumSpacer

@Composable
fun DialogCoverDisplaySelection(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dialogTitle: String,
    confirmText: String,
    action2Text: String,
    descriptionText: String,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
) {
    XenonDialog(
        onDismissRequest = onDismiss,
        title = dialogTitle,
        confirmButtonText = confirmText,
        onConfirmButtonClick = { onConfirm() },
        actionButton2Text = action2Text,
        onActionButton2Click = { onDismiss() },
        mainContextFont = mainContextFont,
        subContextFont = subContextFont,
        properties = DialogProperties(usePlatformDefaultWidth = true),
        contentManagesScrolling = false,
    ) {
        val containerSize = LocalWindowInfo.current.containerSize
        Column {
            Text(
                text = descriptionText,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontFamily = subContextFont,
            )
            Spacer(modifier = Modifier.height(MediumSpacer))
            Text(
                text = "Screen size: ${containerSize.width} x ${containerSize.height} px",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = subContextFont,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
