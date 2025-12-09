package com.xenon.mylibrary.res

// Assume ThemeSetting, SignInState, UserData are now in com.xenon.mylibrary.data
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
    iconContentDescription: String,
    modifier: Modifier = Modifier
) {
    if (profilePictureUrl != null) {
        AsyncImage(
            model = profilePictureUrl,
            contentDescription = "Profile Picture",
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(id = R.mipmap.default_icon),
            contentDescription = iconContentDescription,
            modifier = modifier.size(40.dp)
        )
    }
}