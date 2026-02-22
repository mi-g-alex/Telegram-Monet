package com.c3r5b8.telegram_monet.presentation.image_palette

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import quantize.QuantizerCelebi
import score.Score
import androidx.core.graphics.scale

class ImageProcessor(private val context: Context) {

	suspend fun processImage(uri: Uri): Pair<Bitmap, List<Int>> = withContext(Dispatchers.IO) {
		val displayBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			val source = ImageDecoder.createSource(context.contentResolver, uri)
			ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
				decoder.isMutableRequired = true
				val w = info.size.width
				val h = info.size.height
				val maxDim = 1024
				if (w > maxDim || h > maxDim) {
					val scale = maxDim.toFloat() / maxOf(w, h)
					decoder.setTargetSize(
						(w * scale).toInt().coerceAtLeast(1),
						(h * scale).toInt().coerceAtLeast(1)
					)
				}
			}
		} else {
			val inputStream = context.contentResolver.openInputStream(uri)!!
			val original = BitmapFactory.decodeStream(inputStream)
			inputStream.close()
			val w = original.width
			val h = original.height
			val maxDim = 1024
			if (w > maxDim || h > maxDim) {
				val scale = maxDim.toFloat() / maxOf(w, h)
				original.scale((w * scale).toInt().coerceAtLeast(1), (h * scale).toInt().coerceAtLeast(1))
					.also { if (it !== original) original.recycle() }
			} else {
				original
			}
		}

		val quantBitmap = displayBitmap.scale(512, 512)
		val pixels = IntArray(512 * 512)
		quantBitmap.getPixels(pixels, 0, 512, 0, 0, 512, 512)
		if (quantBitmap !== displayBitmap) quantBitmap.recycle()

		val quantized = QuantizerCelebi.quantize(pixels, 128)
		val scored = Score.score(quantized, 4)
		Pair(displayBitmap, scored)
	}
}
