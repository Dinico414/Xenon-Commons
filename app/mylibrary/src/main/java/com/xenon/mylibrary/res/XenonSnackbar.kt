package com.xenon.mylibrary.res

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.ExtraBiggestBigPadding
import com.xenon.mylibrary.values.LargestPadding
import com.xenon.mylibrary.values.MediumSmallPadding

object XenonSnackbarDefault {
    val backgroundColor: Color @Composable get() = MaterialTheme.colorScheme.inverseSurface
    val contentColor: Color @Composable get() = MaterialTheme.colorScheme.inverseOnSurface
    val actionContainerColor: Color @Composable get() = MaterialTheme.colorScheme.inversePrimary
    val actionContentColor: Color @Composable get() = MaterialTheme.colorScheme.onPrimaryContainer
    val startPadding: Dp = LargestPadding
    val endPadding: Dp = MediumSmallPadding
    val textStyle: TextStyle @Composable get() = TextStyle(
        fontFamily = QuicksandTitleVariable,
        fontWeight = FontWeight.Thin,
        fontSize = 16.sp
    )
    val actionTextStyle: TextStyle @Composable get() = TextStyle(
        fontFamily = QuicksandTitleVariable,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
}

@Suppress("unused")
@Composable
fun XenonSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    contentIcon: (@Composable () -> Unit)? = null,
    actionIcon: (@Composable () -> Unit)? = null,
    backgroundColor: Color = XenonSnackbarDefault.backgroundColor,
    contentColor: Color = XenonSnackbarDefault.contentColor,
    actionContainerColor: Color = XenonSnackbarDefault.actionContainerColor,
    actionContentColor: Color = XenonSnackbarDefault.actionContentColor,
    contentTextStyle: TextStyle = XenonSnackbarDefault.textStyle,
    actionTextStyle: TextStyle = XenonSnackbarDefault.actionTextStyle,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
    padding: Dp = XenonSnackbarDefault.startPadding,
    actionPadding: Dp = XenonSnackbarDefault.endPadding,
    maxLines: Int = 1,
) {
    val resolvedContentStyle = if (mainContextFont != QuicksandTitleVariable) {
        contentTextStyle.copy(fontFamily = mainContextFont)
    } else {
        contentTextStyle
    }
    val resolvedActionStyle = if (subContextFont != null) {
        actionTextStyle.copy(fontFamily = subContextFont)
    } else if (mainContextFont != QuicksandTitleVariable) {
        actionTextStyle.copy(fontFamily = mainContextFont)
    } else {
        actionTextStyle
    }

    Row(
        modifier = modifier
            .heightIn(min = ExtraBiggestBigPadding)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Icon at the start (if present)
        contentIcon?.let {
            Box(
                modifier = Modifier.padding(start = padding),
                contentAlignment = Alignment.Center
            ) {
                it()
            }
        }

        // 2. Message text in the middle
        Text(
            text = snackbarData.visuals.message,
            style = resolvedContentStyle,
            color = contentColor,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = padding,
                    end = if (snackbarData.visuals.actionLabel == null) padding else MediumSmallPadding
                ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )

        // 3. Action button at the end (if present)
        snackbarData.visuals.actionLabel?.let { actionLabel ->
            FilledTonalButton(
                onClick = { snackbarData.performAction() },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = actionContainerColor,
                    contentColor = actionContentColor
                ),
                modifier = Modifier.padding(end = actionPadding)
            ) {
                actionIcon?.let {
                    Box(
                        modifier = Modifier.padding(start = padding),
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
                Text(
                    text = actionLabel,
                    style = resolvedActionStyle
                )
            }
        }
    }
}
