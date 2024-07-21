package com.c3r5b8.telegram_monet.presentation.main_screen

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.c3r5b8.telegram_monet.adapters.createTheme
import com.c3r5b8.telegram_monet.common.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel(
    contextParam: Context
) : ViewModel() {

    var isAmoled = mutableStateOf(false)
    var isGradient = mutableStateOf(false)
    var isAvatarGradient = mutableStateOf(false)
    var isNicknameColorful = mutableStateOf(false)

    private val sharedPreferences: SharedPreferences =
        contextParam.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)

    fun onShareTheme(context: Context, isTelegram: Boolean, isLight: Boolean) {

        val inputFileName = when {
            isTelegram && isLight -> {
                Constants.INPUT_FILE_TELEGRAM_LIGHT
            }
            isTelegram -> {
                Constants.INPUT_FILE_TELEGRAM_DARK
            }
            !isTelegram && isLight -> {
                Constants.INPUT_FILE_TELEGRAM_X_LIGHT
            }
            else -> {
                Constants.INPUT_FILE_TELEGRAM_X_DARK
            }
        }

        val outputFileName = when {
            isTelegram && isLight -> {
                Constants.OUTPUT_FILE_TELEGRAM_LIGHT
            }
            isTelegram && !isAmoled.value -> {
                Constants.OUTPUT_FILE_TELEGRAM_DARK
            }
            isTelegram -> {
                Constants.OUTPUT_FILE_TELEGRAM_AMOLED
            }
            !isTelegram && isLight -> {
                Constants.OUTPUT_FILE_TELEGRAM_X_LIGHT
            }
            !isTelegram && !isAmoled.value -> {
                Constants.OUTPUT_FILE_TELEGRAM_X_DARK
            }
            else -> {
                Constants.OUTPUT_FILE_TELEGRAM_X_AMOLED
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            createTheme(
                context = context,
                isTelegram = isTelegram,
                isAmoled = isAmoled.value,
                isGradient = isGradient.value,
                isAvatarGradient = isAvatarGradient.value,
                isNicknameColorful = isNicknameColorful.value,
                inputFileName = inputFileName,
                outputFileName = outputFileName,
            )
        }
    }

    init {
        getSettings()
    }

    fun setSettings(settings: String, value: Boolean) {
        when (settings) {
            Constants.SHARED_IS_AMOLED -> isAmoled.value = value
            Constants.SHARED_USE_GRADIENT -> isGradient.value = value
            Constants.SHARED_USE_COLORFUL_NICKNAME -> isNicknameColorful.value = value
            Constants.SHARED_USE_GRADIENT_AVATARS -> isAvatarGradient.value = value
        }
        sharedPreferences.edit().putBoolean(settings, value).apply()
    }

    private fun getSettings() {
        isAmoled.value = sharedPreferences.getBoolean("isAmoledMode", false)
        isGradient.value = sharedPreferences.getBoolean("useGradient", false)
        isAvatarGradient.value = sharedPreferences.getBoolean("useGradientAvatars", false)
        isNicknameColorful.value = sharedPreferences.getBoolean("useColorNick", true)
    }

}