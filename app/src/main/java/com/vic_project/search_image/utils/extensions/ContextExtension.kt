package com.vic_project.search_image.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

object ContextExtension {

    fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("no activity")
    }

    fun Context.getBodyFileImg(photo: Uri): MultipartBody.Part {
        val file = this.getFilePathFor(uri = photo)
        val requestFile = file.asRequestBody(
            this.contentResolver.getType(photo)
                ?.toMediaType()
        )
        return  MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    @SuppressLint("Recycle")
    fun Context.getFilePathFor(uri: Uri): File {
        val returnCursor = this.contentResolver.query(uri, null, null, null, null)

        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val file = File(this.filesDir, name)
        try {
            val inputStream = this.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable = inputStream!!.available()

            //int bufferSize = 1024;
            val bufferSize = bytesAvailable.coerceAtMost(maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream.read(buffers).also { read = it } != -1) {
                outputStream.write(buffers, 0, read)
            }
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            Log.e("ExceptionInputStream", e.message!!)
        }
        return file
    }
}