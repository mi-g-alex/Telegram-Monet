package com.c3r5b8.telegram_monet.adapters

import com.c3r5b8.telegram_monet.common.ColorScheme
import hct.Hct
import scheme.SchemeContent
import scheme.SchemeExpressive
import scheme.SchemeFidelity
import scheme.SchemeFruitSalad
import scheme.SchemeMonochrome
import scheme.SchemeNeutral
import scheme.SchemeRainbow
import scheme.SchemeTonalSpot
import scheme.SchemeVibrant

val PALETTE_TONES = listOf(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95, 99, 100)
private val ANDROID_TO_HCT_TONE = PALETTE_TONES.associateBy { 1000 - it * 10 }

private const val DEFAULT_CONTRAST = 0.0

fun schemeFor(
	argb: Int,
	colorScheme: ColorScheme,
	isDark: Boolean = false,
) =
    Hct.fromInt(argb).let { hct ->
        when (colorScheme) {
            ColorScheme.TONAL_SPOT -> SchemeTonalSpot(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.FIDELITY -> SchemeFidelity(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.VIBRANT -> SchemeVibrant(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.NEUTRAL -> SchemeNeutral(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.EXPRESSIVE -> SchemeExpressive(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.FRUIT_SALAD -> SchemeFruitSalad(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.RAINBOW -> SchemeRainbow(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.CONTENT -> SchemeContent(hct, isDark, DEFAULT_CONTRAST)
            ColorScheme.MONOCHROME -> SchemeMonochrome(hct, isDark, DEFAULT_CONTRAST)
        }
    }

fun generateMonetColors(
    seedArgb: Int,
    colorScheme: ColorScheme = ColorScheme.TONAL_SPOT,
): Map<String, Int> {
    val scheme = schemeFor(seedArgb, colorScheme)
    val palettes = mapOf(
        "a1" to scheme.primaryPalette,
        "a2" to scheme.secondaryPalette,
        "a3" to scheme.tertiaryPalette,
        "n1" to scheme.neutralPalette,
        "n2" to scheme.neutralVariantPalette,
    )
    return buildMap {
        palettes.forEach { (prefix, palette) ->
            ANDROID_TO_HCT_TONE.forEach { (androidTone, hctTone) ->
                put("${prefix}_${androidTone}", palette.tone(hctTone))
            }
        }
    }
}
