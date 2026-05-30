package com.c3r5b8.telegram_monet.adapters

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import com.c3r5b8.telegram_monet.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DOWNLOADS_SUBDIR = "TgMonet"

fun saveThemeToDownloads(context: Context, outputFileName: String, content: String) {
	val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
	val base = outputFileName.substringBeforeLast(".")
	val ext = outputFileName.substringAfterLast(".", "")
	val finalName = if (ext.isEmpty()) "${base}_$timestamp" else "${base}_$timestamp.$ext"

	val ok = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
		saveViaMediaStore(context, finalName, content)
	} else {
		saveToLegacyDownloads(finalName, content)
	}

	val msg = if (ok) context.getString(R.string.save_to_downloads_success, finalName)
	else context.getString(R.string.save_to_downloads_failed)
	Handler(Looper.getMainLooper()).post {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
	}
}

private fun saveViaMediaStore(context: Context, finalName: String, content: String): Boolean {
	return runCatching {
		val values = ContentValues().apply {
			put(MediaStore.Downloads.DISPLAY_NAME, finalName)
			put(MediaStore.Downloads.MIME_TYPE, "application/octet-stream")
			put(
				MediaStore.Downloads.RELATIVE_PATH,
				"${Environment.DIRECTORY_DOWNLOADS}/$DOWNLOADS_SUBDIR"
			)
		}
		val resolver = context.contentResolver
		val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
			?: return@runCatching false
		resolver.openOutputStream(uri)?.use { it.write(content.toByteArray()) }
			?: return@runCatching false
		true
	}.getOrDefault(false)
}

private fun saveToLegacyDownloads(finalName: String, content: String): Boolean {
	return runCatching {
		@Suppress("DEPRECATION")
		val dir = File(
			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
			DOWNLOADS_SUBDIR
		)
		if (!dir.exists()) dir.mkdirs()
		File(dir, finalName).writeText(content)
		true
	}.getOrDefault(false)
}
