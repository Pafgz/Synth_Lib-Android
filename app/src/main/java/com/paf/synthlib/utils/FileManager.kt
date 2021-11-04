package com.paf.synthlib.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.Instant
import java.util.*

class FileManager(private val context: Context) {

    private val contentResolver = context.contentResolver

    suspend fun saveImage(folder: String, uri: Uri) = withContext(Dispatchers.IO) {
        val bitmap = if (isContentUri(uri)) {
            getBitmapFromContentUri(uri)
        } else {
            uri.getBitmap()
        }
        saveImage(folder, bitmap)
    }

    suspend fun saveImage(folder: String, bmp: Bitmap) = withContext(Dispatchers.IO) {
        val imageOutStream: OutputStream?
        val fileName = Date.from(Instant.now()).time.toString()
        val folderName = "$SYSTEM_FOLDER/$APP_FOLDER/$folder"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                //image name
                put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
                // image type
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                //storage path
                put(MediaStore.Images.Media.RELATIVE_PATH, folderName)
            }

            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageOutStream = uri?.let { context.contentResolver.openOutputStream(it) }
        } else {
            //creating directory and saving
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(folderName).toString()
            val image = File(imagesDir, "$fileName.jpg")
            imageOutStream = FileOutputStream(image)
        }
        //compressing the image
        imageOutStream?.let {
            bmp.compress(Bitmap.CompressFormat.JPEG, 95, it)
            Log.d("FileManager", "Image Saved")
            it.close()
        } ?: Log.w("FileManager", "imageOutStream is null")
    }

    suspend fun getBitmapFromContentUri(uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap: Bitmap
        inputStream.use {
            bitmap = BitmapFactory.decodeStream(it)
        }
        return bitmap
    }

    private fun isContentUri(uri: Uri): Boolean {
        var isContentUri = false
        val uriSchema = uri.scheme
        if ("content".equals(uriSchema, ignoreCase = true)) {
            isContentUri = true
        }
        return isContentUri
    }

    companion object {
        private const val SYSTEM_FOLDER = "Pictures"
        private const val APP_FOLDER = "SynthLib"
    }
}