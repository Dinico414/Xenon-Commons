package com.xenon.mylibrary.res

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xenon.mylibrary.QuicksandTitleVariable
import com.xenon.mylibrary.values.ExtraLargePadding
import com.xenon.mylibrary.values.LargeCornerRadius
import com.xenon.mylibrary.values.LargestPadding

@Suppress("unused")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsGoogleTile(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    profilePictureUrl: String? = null,
    isSignedIn: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    arrowColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shape: Shape = RoundedCornerShape(LargeCornerRadius),
    horizontalPadding: Dp = LargestPadding,
    verticalPadding: Dp = ExtraLargePadding,
    iconContentDescription: String = "Profile picture",
    noAccIcon: Painter
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .combinedClickable(
                onClick = { onClick?.invoke() },
                onLongClick = { onLongClick?.invoke() },
                role = Role.Button,
                enabled = onClick != null || onLongClick != null
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ExtraLargePadding)
    ) {
        Box(contentAlignment = Alignment.Center) {
            GoogleProfilBorder(
                isSignedIn = isSignedIn,
                modifier = Modifier.size(48.dp),
                strokeWidth = 2.5.dp
            )
            GoogleProfilePicture(
                noAccIcon = noAccIcon,
                profilePictureUrl = profilePictureUrl,
                contentDescription = iconContentDescription,
                modifier = Modifier.size(40.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = QuicksandTitleVariable
                ),
                color = contentColor
            )
            if (!subtitle.isNullOrEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = QuicksandTitleVariable
                    ),
                    color = subtitleColor
                )
            }
        }

        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "Navigate",
            tint = arrowColor,
            modifier = Modifier.size(24.dp)
        )
    }
}