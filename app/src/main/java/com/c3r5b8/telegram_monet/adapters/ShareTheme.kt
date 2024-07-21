package com.c3r5b8.telegram_monet.adapters

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

fun shareTheme(
    context: Context,
    fileName: String
) {
    val file = File(context.filesDir, fileName)

    val uri = FileProvider.getUriForFile(
        context,
        "com.c3r5b8.telegram_monet.provider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "document/*"
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(Intent.createChooser(intent, fileName))
}