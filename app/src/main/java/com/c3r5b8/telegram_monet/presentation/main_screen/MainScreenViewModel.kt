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
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(
	contextParam: Context,
) : ViewModel() {

    private val _isAmoled = MutableStateFlow(false)
    val isAmoled: StateFlow<Boolean> = _isAmoled.asStateFlow()

    private val _isGradient = MutableStateFlow(false)
    val isGradient: StateFlow<Boolean> = _isGradient.asStateFlow()

    private val _isAvatarGradient = MutableStateFlow(false)
    val isAvatarGradient: StateFlow<Boolean> = _isAvatarGradient.asStateFlow()

    private val _isNicknameColorful = MutableStateFlow(false)
    val isNicknameColorful: StateFlow<Boolean> = _isNicknameColorful.asStateFlow()

    private val _isAlterOutColor = MutableStateFlow(false)
    val isAlterOutColor: StateFlow<Boolean> = _isAlterOutColor.asStateFlow()

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
                isAlterOutColor = isAlterOutColor.value,
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
            Constants.SHARED_IS_AMOLED -> _isAmoled.value = value
            Constants.SHARED_USE_GRADIENT -> _isGradient.value = value
            Constants.SHARED_USE_COLORFUL_NICKNAME -> _isNicknameColorful.value = value
            Constants.SHARED_USE_GRADIENT_AVATARS -> _isAvatarGradient.value = value
            Constants.SHARED_USE_OLD_CHAT_STYLE -> _isAlterOutColor.value = value
        }
        sharedPreferences.edit { putBoolean(settings, value) }
    }

    private fun getSettings() {
        _isAmoled.value =
            sharedPreferences.getBoolean(Constants.SHARED_IS_AMOLED, false)
        _isGradient.value =
            sharedPreferences.getBoolean(Constants.SHARED_USE_GRADIENT, false)
        _isAvatarGradient.value =
            sharedPreferences.getBoolean(Constants.SHARED_USE_GRADIENT_AVATARS, false)
        _isNicknameColorful.value =
            sharedPreferences.getBoolean(Constants.SHARED_USE_COLORFUL_NICKNAME, true)
        _isAlterOutColor.value =
            sharedPreferences.getBoolean(Constants.SHARED_USE_OLD_CHAT_STYLE, true)
    }

}
