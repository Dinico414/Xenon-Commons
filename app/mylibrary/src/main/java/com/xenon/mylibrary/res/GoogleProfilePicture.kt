package com.xenon.mylibrary.res

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xenon.mylibrary.R

@Composable
fun GoogleProfilePicture(
    profilePictureUrl: String?,
//    fallbackPainter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    if (!profilePictureUrl.isNullOrBlank()) {
        AsyncImage(
            model = profilePictureUrl,
            contentDescription = contentDescription,
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    } else {
        Image(
            painter = painterResource(id = R.mipmap.default_icon),
            contentDescription = contentDescription,
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}