package com.xenon.mylibrary.utils

import android.content.Context

data class PermissionItem(
    val name: String,
    val description: String,
    val isGranted: (Context) -> Boolean,
    val request: (Context) -> Unit
)