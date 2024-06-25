package com.bayu.narutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageUrlToBitmap(
        context: Context,
        imageUrl: String
    ): Bitmap? {
        val loader = context.imageLoader
        val request = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(request)
        return if (imageResult is SuccessResult) (imageResult.drawable as? BitmapDrawable)?.bitmap else null
    }

    fun extractColorFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().vibrantSwatch
            ),
            "darkVibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            "onDarkVibrant" to parseBoydcolor(
                color = Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            ),
        )
    }

    fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            "#$parsedColor"
        } else {
            "#000000"
        }
    }

    fun parseBoydcolor(color: Int?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color)
            "#$parsedColor"
        } else {
            "#FFFFFF"
        }
    }

}