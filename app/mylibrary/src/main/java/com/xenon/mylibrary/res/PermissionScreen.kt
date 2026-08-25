package com.xenon.mylibrary.res

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.utils.PermissionItem

@Composable
fun PermissionScreen(
    permissions: List<PermissionItem>,
    isFirstLaunch: Boolean = false,
    grantButtonText: String = "Grant Permission",
    nextButtonText: String = "Next",
    finishButtonText: String = "Finish",
    onFinish: () -> Unit
) {
    var currentPermissionIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

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

    DisposableEffect(currentPermissionIndex) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isPermissionGranted = currentPermission.isGranted(context)
                if (isPermissionGranted) {
                    val nextIndex = permissions.indexOfFirst { !it.isGranted(context) }
                    if (nextIndex != -1) {
                        currentPermissionIndex = nextIndex
                    } else {
                        onFinish()
                    }
                }
            }
        }
        (context as ComponentActivity).lifecycle.addObserver(observer)
        onDispose { context.lifecycle.removeObserver(observer) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(16.dp),
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
                fontFamily = QuicksandTitleVariable,
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                if (isPermissionGranted) {
                    val nextIndex = permissions.indexOfFirst { !it.isGranted(context) }
                    if (nextIndex != -1) {
                        currentPermissionIndex = nextIndex
                    } else {
                        onFinish()
                    }
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
                .height(96.dp)
        ) {
            val allGranted = permissions.all { it.isGranted(context) }
            Text(
                text = when {
                    allGranted && !isFirstLaunch -> finishButtonText
                    isPermissionGranted && !allGranted -> nextButtonText
                    else -> grantButtonText
                },
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = QuicksandTitleVariable,
            )
        }
    }
}