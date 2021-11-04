package com.paf.synthlib.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

suspend fun Uri.getBitmap(): Bitmap = BitmapFactory.decodeFile(path)
