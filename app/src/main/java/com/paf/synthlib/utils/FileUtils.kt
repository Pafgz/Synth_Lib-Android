package com.paf.synthlib.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun createTempFile(prefix: String, suffix: String): File = withContext(Dispatchers.IO) {
    kotlin.runCatching {
        File.createTempFile(prefix, suffix)
    }.getOrElse { ex ->
        Log.e("Temp File", "Failed to create temporary file", ex)
        File("/dev/null")
    }
}