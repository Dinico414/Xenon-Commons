package com.xenon.mylibrary.res

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.ExtraLargePadding
import com.xenon.mylibrary.values.LargerCornerRadius
import com.xenon.mylibrary.values.LargerPadding
import com.xenon.mylibrary.values.NoPadding
import com.xenon.mylibrary.values.SmallerCornerRadius

@Suppress("unused")
@Composable
fun XenonDrawer(
    title: String,
    profilePictureUrl: String? = null,
    isSignedIn: Boolean = false,
    noAccIcon: Painter,
    backgroundColor: Color = colorScheme.surfaceContainerHigh,
    profilePicDesc: String? = null,
    hasBottomContent: Boolean = false,
    bottomContent: (@Composable ColumnScope.() -> Unit)? = null,
    contentManagesScrolling: Boolean = false,
    content: @Composable (scrollState: ScrollState?) -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val safeDrawingInsets = WindowInsets.safeDrawing.asPaddingValues()

    val startPadding =
        if (safeDrawingInsets.calculateStartPadding(layoutDirection) > 0.dp) NoPadding else LargerPadding
    val topPadding =
        if (safeDrawingInsets.calculateTopPadding() > 0.dp) NoPadding else LargerPadding
    val bottomPadding =
        if (safeDrawingInsets.calculateBottomPadding() > 0.dp) NoPadding else LargerPadding

    ModalDrawerSheet(drawerContainerColor = Color.Transparent) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(start = startPadding, top = topPadding, bottom = bottomPadding)
                .clip(
                    RoundedCornerShape(
                        topStart = SmallerCornerRadius,
                        bottomStart = SmallerCornerRadius,
                        topEnd = LargerCornerRadius,
                        bottomEnd = LargerCornerRadius
                    )
                )
                .background(backgroundColor)
        ) {
            Column(Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = ExtraLargePadding)
                        .padding(top = ExtraLargePadding)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = title, style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = QuicksandTitleVariable, color = colorScheme.onSurface
                            ), modifier = Modifier
                                .weight(1f)
                                .padding(bottom = ExtraLargePadding)
                        )
                        if (isSignedIn || profilePictureUrl != null) {
                            Box(contentAlignment = Alignment.Center) {
                                GoogleProfilBorder(
                                    isSignedIn = isSignedIn,
                                    modifier = Modifier.size(32.dp),
                                    strokeWidth = 2.5.dp
                                )
                                GoogleProfilePicture(
                                    noAccIcon = noAccIcon,
                                    profilePictureUrl = profilePictureUrl,
                                    contentDescription = profilePicDesc ?: "",
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                    HorizontalDivider(thickness = 1.dp, color = colorScheme.outlineVariant)
                }
                if (contentManagesScrolling) {
                    Box(modifier = Modifier.weight(1f)) {
                        content(null)
                    }
                } else {
                    val scrollState = rememberScrollState()
                    val isAtBottom by remember {
                        derivedStateOf { scrollState.value >= scrollState.maxValue - 8 }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .fillMaxWidth()
                                .padding(horizontal = ExtraLargePadding)
                        ) {
                            content(scrollState)
                        }
                        val showDivider = !hasBottomContent && !isAtBottom
                        HorizontalDivider(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = ExtraLargePadding)
                                .alpha(if (showDivider) 1f else 0f),
                            thickness = 1.dp,
                            color = colorScheme.outlineVariant
                        )
                    }
                }
                if (hasBottomContent) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = ExtraLargePadding),
                        thickness = 1.dp,
                        color = colorScheme.outlineVariant
                    )
                }
                bottomContent?.let { bottom ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = ExtraLargePadding)
                            .padding(top = ExtraLargePadding)
                    ) {
                        bottom()
                    }
                }
                Spacer(Modifier.height(ExtraLargePadding))
            }
        }
    }
}