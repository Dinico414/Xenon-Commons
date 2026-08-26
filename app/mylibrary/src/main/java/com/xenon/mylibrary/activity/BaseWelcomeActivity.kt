package com.xenon.mylibrary.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.xenon.mylibrary.res.AnimatedGradientBackground
import com.xenon.mylibrary.res.WelcomeScreen
import com.xenon.mylibrary.theme.XenonTheme // Or your library theme

abstract class BaseWelcomeActivity : ComponentActivity() {

    abstract fun getTitleText(): String
    abstract fun getDescriptionText(): String
    abstract fun onWelcomeFinished()

    open fun getCountdownSeconds(): Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            XenonTheme(darkTheme = isSystemInDarkTheme()) {
                AnimatedGradientBackground {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Transparent
                    ) {
                        WelcomeScreen(
                            title = getTitleText(),
                            description = getDescriptionText(),
                            initialCountdown = getCountdownSeconds(),
                            onFinish = { onWelcomeFinished() }
                        )
                    }
                }
            }
        }
    }
}