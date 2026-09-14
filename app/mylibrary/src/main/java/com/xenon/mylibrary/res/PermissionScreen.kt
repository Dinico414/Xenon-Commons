package com.xenon.mylibrary.res

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.xenon.mylibrary.R
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.utils.PermissionItem
import com.xenon.mylibrary.utils.openAppInfo
import com.xenon.mylibrary.values.ExtraBigPadding
import com.xenon.mylibrary.values.HugePadding
import com.xenon.mylibrary.values.LargeButtonSize
import com.xenon.mylibrary.values.LargestPadding
import com.xenon.mylibrary.values.MediumIconSize
import com.xenon.mylibrary.values.MediumPadding

@Composable
fun PermissionScreen(
    permissions: List<PermissionItem>,
    isFirstLaunch: Boolean = false,
    grantButtonText: String = stringResource(R.string.grant_permission),
    skipButtonText: String = stringResource(R.string.skip),
    nextButtonText: String = "Next",
    finishButtonText: String = "Finish",
    showSkipButton: Boolean = true,
    showInfoButton: Boolean = true,
    defaultGuideText: String? = null,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
    skipIcon: XenonIcon? = null,
    onSkip: ((PermissionItem) -> Unit)? = null,
    onFinish: () -> Unit,
) {
    var currentPermissionIndex by remember { mutableIntStateOf(0) }
    val skippedPermissionIndices = remember { mutableStateListOf<Int>() }
    val context = LocalContext.current
    var showGuide by remember(currentPermissionIndex) { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val firstUngranted = permissions.indexOfFirst { !it.isGranted(context) }
        if (firstUngranted != -1) {
            currentPermissionIndex = firstUngranted
        } else {
            onFinish()
        }
    }

    val currentPermission = permissions.getOrNull(currentPermissionIndex)
    if (currentPermission == null) {
        LaunchedEffect(Unit) { onFinish() }
        return
    }

    var isPermissionGranted by remember(currentPermissionIndex) {
        mutableStateOf(currentPermission.isGranted(context))
    }

    val advanceToNext: () -> Unit = {
        showGuide = false
        val nextIndex = ((currentPermissionIndex + 1) until permissions.size).firstOrNull { index ->
            !permissions[index].isGranted(context) && index !in skippedPermissionIndices
        } ?: permissions.indices.firstOrNull { index ->
            !permissions[index].isGranted(context) && index !in skippedPermissionIndices
        }
        if (nextIndex != null) {
            currentPermissionIndex = nextIndex
        } else {
            onFinish()
        }
    }

    DisposableEffect(currentPermissionIndex) {
        val activity = (context as? ComponentActivity) ?: return@DisposableEffect onDispose {}
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isPermissionGranted = currentPermission.isGranted(context)
                if (isPermissionGranted) {
                    advanceToNext()
                }
            }
        }
        activity.lifecycle.addObserver(observer)
        onDispose { activity.lifecycle.removeObserver(observer) }
    }

    val guideText = currentPermission.guideText
        ?: currentPermission.infoText
        ?: defaultGuideText
        ?: stringResource(R.string.accessibility_guide)

    val handleInfoClick: () -> Unit = {
        if (currentPermission.onInfoClick != null) {
            currentPermission.onInfoClick.invoke(context)
        } else {
            openAppInfo(context)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.safeDrawing.asPaddingValues())
                .padding(LargestPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentPermission.name,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        hyphens = Hyphens.Auto,
                        lineBreak = LineBreak.Paragraph,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(2f, 4f),
                            blurRadius = 8f
                        )
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    fontFamily = mainContextFont,
                )
                Spacer(modifier = Modifier.height(LargestPadding))
                Text(
                    text = currentPermission.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        hyphens = Hyphens.Auto,
                        lineBreak = LineBreak.Paragraph,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(1f, 2f),
                            blurRadius = 2f
                        )
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    fontFamily = subContextFont,
                )
            }

            if (showSkipButton && !isPermissionGranted) {
                Row(
                    modifier = Modifier
                        .widthIn(max = 420.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LargestPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { currentPermission.request(context) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.inverseSurface,
                            contentColor = MaterialTheme.colorScheme.inverseOnSurface
                        ),
                        modifier = Modifier
                            .weight(2f)
                            .height(LargeButtonSize)
                    ) {
                        Text(
                            text = grantButtonText,
                            style = MaterialTheme.typography.headlineSmall,
                            fontFamily = mainContextFont,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }

                    Button(
                        onClick = {
                            skippedPermissionIndices.add(currentPermissionIndex)
                            onSkip?.invoke(currentPermission)
                            advanceToNext()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(LargeButtonSize)
                    ) {
                        val effectiveSkipIcon = currentPermission.skipIcon ?: skipIcon
                        if (effectiveSkipIcon != null) {
                            effectiveSkipIcon.Render(Modifier.size(MediumIconSize))
                        } else {
                            Text(
                                text = skipButtonText,
                                style = MaterialTheme.typography.titleLarge,
                                fontFamily = mainContextFont,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
            } else {
                Button(
                    onClick = {
                        if (isPermissionGranted) {
                            advanceToNext()
                        } else {
                            currentPermission.request(context)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    modifier = Modifier
                        .widthIn(max = 420.dp)
                        .fillMaxWidth()
                        .height(LargeButtonSize)
                ) {
                    val allGranted = permissions.all { it.isGranted(context) }
                    Text(
                        text = when {
                            allGranted && !isFirstLaunch -> finishButtonText
                            isPermissionGranted && !allGranted -> nextButtonText
                            else -> grantButtonText
                        },
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = mainContextFont,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (showGuide) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showGuide = false }
            )
        }

        if (showInfoButton) {
            IconButton(
                onClick = {
                    if (showGuide) {
                        handleInfoClick()
                    } else {
                        showGuide = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                .padding(top = MediumPadding, end = LargestPadding)
            ) {
                Icon(
                    Icons.Rounded.Info,
                    contentDescription = "Info Guide",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        AnimatedVisibility(
            visible = showGuide,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(WindowInsets.safeDrawing.asPaddingValues())
                .padding(top = HugePadding)
                .padding(horizontal = ExtraBigPadding)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(LargestPadding))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.95f))
                    .clickable {
                        handleInfoClick()
                    }
                    .padding(LargestPadding)
            ) {
                Text(
                    text = guideText,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = subContextFont,
                )
            }
        }
    }
}
