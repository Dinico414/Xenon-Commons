@file:Suppress("unused")

package com.xenon.mylibrary.previews

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.xenon.mylibrary.res.TopContentBar
import com.xenon.mylibrary.res.XenonColorPicker
import com.xenon.mylibrary.res.XenonDialog
import com.xenon.mylibrary.res.XenonSingleChoiceButtonGroup
import com.xenon.mylibrary.res.XenonSnackbar
import com.xenon.mylibrary.res.XenonTextField
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.LargestPadding

@Preview(name = "Dialog", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun XenonDialogPreview() {
    PreviewScaffold {
        XenonDialog(
            onDismissRequest = {},
            title = "Delete note?",
            confirmButtonText = "Delete",
            onConfirmButtonClick = {},
            actionButton1Text = "Cancel",
            onActionButton1Click = {},
        ) {
            Text(
                text = "The note will be removed from this device.",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = QuicksandTitleVariable,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(name = "Dialog · dark", widthDp = 420, heightDp = 720, showBackground = true)
@Composable
private fun XenonDialogDarkPreview() {
    PreviewScaffold(darkTheme = true) {
        XenonDialog(
            onDismissRequest = {},
            title = "Delete note?",
            confirmButtonText = "Delete",
            onConfirmButtonClick = {},
            actionButton1Text = "Cancel",
            onActionButton1Click = {},
        ) {
            Text(
                text = "The note will be removed from this device.",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = QuicksandTitleVariable,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(name = "Snackbar", widthDp = 412, heightDp = 160, showBackground = true)
@Composable
private fun XenonSnackbarPreview() {
    PreviewScaffold {
        XenonSnackbar(
            snackbarData = PreviewSnackbarData(
                PreviewSnackbarVisuals(message = "Note archived", actionLabel = "Undo"),
            ),
            modifier = Modifier.padding(LargestPadding),
        )
    }
}

@Preview(name = "Snackbar · no action", widthDp = 412, heightDp = 160, showBackground = true)
@Composable
private fun XenonSnackbarPlainPreview() {
    PreviewScaffold {
        XenonSnackbar(
            snackbarData = PreviewSnackbarData(
                PreviewSnackbarVisuals(message = "All notes synced"),
            ),
            modifier = Modifier.padding(LargestPadding),
        )
    }
}

@Preview(name = "TextField", widthDp = 412, heightDp = 200, showBackground = true)
@Composable
private fun XenonTextFieldPreview() {
    PreviewScaffold {
        var value by remember { mutableStateOf("Buy oat milk") }
        XenonTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.padding(LargestPadding),
            placeholder = { Text("Note title") },
            leadingIcon = {
                Icon(Icons.Rounded.Person, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { value = "" }) {
                    Icon(Icons.Rounded.Clear, contentDescription = "Clear")
                }
            },
        )
    }
}

@Preview(name = "TextField · error", widthDp = 412, heightDp = 200, showBackground = true)
@Composable
private fun XenonTextFieldErrorPreview() {
    PreviewScaffold {
        XenonTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(LargestPadding),
            isError = true,
            placeholder = { Text("Required field") },
        )
    }
}

@Preview(name = "ColorPicker", widthDp = 412, heightDp = 520, showBackground = true)
@Composable
private fun XenonColorPickerPreview() {
    PreviewScaffold {
        var color by remember { mutableStateOf(Color(0xFF6C4CE0)) }
        XenonColorPicker(
            color = color,
            onColorChanged = { color = it },
            modifier = Modifier.padding(LargestPadding),
        )
    }
}

@Preview(name = "ChoiceGroup", widthDp = 412, heightDp = 320, showBackground = true)
@Composable
private fun XenonSingleChoiceButtonGroupPreview() {
    PreviewScaffold {
        val options = listOf("Day", "Week", "Month")
        XenonSingleChoiceButtonGroup(
            options = options,
            selectedOption = "Week",
            onOptionSelect = {},
            label = { it },
            modifier = Modifier
                .padding(LargestPadding)
                .align(Alignment.TopCenter),
        )
    }
}

@Preview(name = "TopContentBar · title", widthDp = PreviewSizes.CompactWidth, heightDp = 160)
@Composable
private fun TopContentBarTitlePreview() {
    PreviewScaffold {
        TopContentBar(
            modifier = Modifier.padding(top = LargestPadding),
            title = "All notes",
            onNavigationClick = {},
            menuItems = previewMenuItems,
        )
    }
}

@Preview(name = "TopContentBar · search", widthDp = PreviewSizes.CompactWidth, heightDp = 160)
@Composable
private fun TopContentBarSearchPreview() {
    PreviewScaffold {
        var query by remember { mutableStateOf("") }
        TopContentBar(
            modifier = Modifier.padding(top = LargestPadding),
            value = query,
            onValueChange = { query = it },
            placeholder = "Search notes",
            onNavigationClick = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Person, contentDescription = null)
                }
            },
        )
    }
}

@Preview(name = "TopContentBar · dark", widthDp = PreviewSizes.CompactWidth, heightDp = 160)
@Composable
private fun TopContentBarDarkPreview() {
    PreviewScaffold(darkTheme = true) {
        TopContentBar(
            modifier = Modifier.padding(top = LargestPadding),
            title = "Archive",
            blurEnabled = false,
            menuItems = previewMenuItems,
        )
    }
}
