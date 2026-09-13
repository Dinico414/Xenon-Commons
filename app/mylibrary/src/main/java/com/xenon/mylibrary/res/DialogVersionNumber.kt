package com.xenon.mylibrary.res

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.SmallPadding

@Suppress("unused")
@Composable
fun DialogVersionNumber(
    onDismiss: () -> Unit,
    dialogTitle: String,
    confirmText: String,
    appString: String,
    appVersion: String,
    xenonUiString: String,
    xenonUIVersion: String,
    xenonCommonsString: String,
    xenonCommonsVersion: String,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
) {
    val context = LocalContext.current

    val onMoreInfoClick: () -> Unit = {
        onDismiss()

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    XenonDialog(
        onDismissRequest = onDismiss,
        title = dialogTitle,
        confirmButtonText = confirmText,
        onConfirmButtonClick = onMoreInfoClick,
        mainContextFont = mainContextFont,
        subContextFont = subContextFont,
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SmallPadding)
        ) {
            VersionItem(label = appString, version = appVersion, font = subContextFont)
            VersionItem(label = xenonUiString, version = xenonUIVersion, font = subContextFont)
            VersionItem(label = xenonCommonsString, version = xenonCommonsVersion, font = subContextFont)
        }
    }
}

@Composable
private fun VersionItem(label: String, version: String, font: FontFamily? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = font,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = version,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            fontFamily = font,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
