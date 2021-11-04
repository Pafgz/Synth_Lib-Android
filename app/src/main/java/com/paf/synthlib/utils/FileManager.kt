package com.paf.synthlib.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.Instant
import java.util.*

class FileManager(private val context: Context) {

    private val contentResolver = context.contentResolver

    suspend fun saveImage(folder: String, bmp: Uri) = withContext(Dispatchers.IO) {
        var imageOutStream: OutputStream? = null
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
            val bitmap: Bitmap = bmp.getBitmap(contentResolver)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)
            Log.d("FileManager", "Image Saved")
            it.close()
        } ?: Log.w("FileManager", "imageOutStream is null")
    }

//    suspend fun getImagesFrom(folder: String): List<Uri> = withContext(Dispatchers.IO) {
//
//    }

    companion object {
        private const val SYSTEM_FOLDER = "Pictures"
        private const val APP_FOLDER = "SynthLib"
    }
}