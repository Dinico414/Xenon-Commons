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
import com.xenon.mylibrary.res.PermissionScreen
import com.xenon.mylibrary.theme.XenonTheme
import com.xenon.mylibrary.utils.PermissionItem

abstract class BasePermissionActivity : ComponentActivity() {

    /** Override this to supply the list of permissions required by your app */
    abstract fun getPermissions(): List<PermissionItem>

    /** Called when all permissions are granted */
    abstract fun onPermissionsFinished()

    /** Override to check if this is the first launch */
    open fun isFirstLaunch(): Boolean = false

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
                        PermissionScreen(
                            permissions = getPermissions(),
                            isFirstLaunch = isFirstLaunch(),
                            onFinish = { onPermissionsFinished() }
                        )
                    }
                }
            }
        }
    }
}