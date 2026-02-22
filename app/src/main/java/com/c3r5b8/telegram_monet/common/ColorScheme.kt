package com.c3r5b8.telegram_monet.common

import androidx.annotation.StringRes
import com.c3r5b8.telegram_monet.R

enum class ColorScheme(@param:StringRes val resName: Int) {
    TONAL_SPOT(R.string.scheme_name_tonal_spot),
    FIDELITY(R.string.scheme_name_fidelity),
    VIBRANT(R.string.scheme_name_videlity),
    NEUTRAL(R.string.scheme_name_neutral),
    EXPRESSIVE(R.string.scheme_name_expressive),
    FRUIT_SALAD(R.string.scheme_name_fruit_salad),
    RAINBOW(R.string.scheme_name_rainbow),
    CONTENT(R.string.scheme_name_content),
    MONOCHROME(R.string.scheme_name_monochrome);

    companion object {
        fun fromName(name: String): ColorScheme =
            entries.firstOrNull { it.name == name } ?: TONAL_SPOT
    }
}
