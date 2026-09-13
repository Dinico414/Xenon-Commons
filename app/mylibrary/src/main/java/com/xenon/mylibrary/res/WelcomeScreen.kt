package com.xenon.mylibrary.res

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.LargeButtonSize
import com.xenon.mylibrary.values.LargestPadding
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun WelcomeScreen(
    title: String,
    description: String,
    finishButtonText: String = "Finish",
    initialCountdown: Int = 5,
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
    onFinish: () -> Unit,
) {
    var isButtonEnabled by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(initialCountdown) }

    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000.milliseconds)
            countdown--
        }
        isButtonEnabled = true
    }

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
                text = title,
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
                text = description,
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

        Button(
            onClick = onFinish,
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inverseSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface
            ),
            modifier = Modifier
                .widthIn(max = 420.dp)
                .fillMaxWidth()
                .height(LargeButtonSize)
        ) {
            Text(
                text = if (isButtonEnabled) finishButtonText else "$finishButtonText ($countdown)",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = mainContextFont,
            )
        }
    }
}