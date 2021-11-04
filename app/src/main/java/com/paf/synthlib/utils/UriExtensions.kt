package com.paf.synthlib.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Uri.getBitmap(contentResolver: ContentResolver): Bitmap =
    BitmapFactory.decodeFile(path)
//    val parcelFileDescriptor = contentResolver.openFileDescriptor(this, "r")
//    val fileDescriptor = parcelFileDescriptor?.fileDescriptor
//    val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
//    parcelFileDescriptor?.close()
