package com.xenon.mylibrary.res

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.xenon.mylibrary.values.CompactButtonSize

@Composable
fun GoogleProfilePicture(
    profilePictureUrl: String?,
    noAccIcon: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val loadingBackground = Color(0xFFD4D4D4)

    if (!profilePictureUrl.isNullOrBlank()) {
        AsyncImage(
            model = profilePictureUrl,
            contentDescription = contentDescription,
            modifier = modifier
                .size(CompactButtonSize)
                .clip(CircleShape)
                .background(loadingBackground),
            contentScale = ContentScale.Crop,
        )
    } else {
        Image(
            painter = noAccIcon,
            contentDescription = contentDescription,
            modifier = modifier
                .size(CompactButtonSize)
                .clip(CircleShape)
        )
    }
}