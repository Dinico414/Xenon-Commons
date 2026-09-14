package com.xenon.mylibrary.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.xenon.mylibrary.res.XenonIcon

data class PermissionItem(
    val name: String,
    val description: String,
    val isGranted: (Context) -> Boolean,
    val request: (Context) -> Unit,
    val guideText: String? = null,
    val infoText: String? = guideText,
    val onInfoClick: ((Context) -> Unit)? = null,
    val skipIcon: XenonIcon? = null
)

fun openAppInfo(context: Context) {
    try {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (_: Exception) {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
        }
    }
}
