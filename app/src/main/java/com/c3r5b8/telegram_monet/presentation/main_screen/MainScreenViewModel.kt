package com.c3r5b8.telegram_monet.presentation.main_screen

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3r5b8.telegram_monet.adapters.createTheme
import com.c3r5b8.telegram_monet.common.ColorScheme
import com.c3r5b8.telegram_monet.common.Constants
import com.c3r5b8.telegram_monet.data.local.entity.PaletteEntity
import com.c3r5b8.telegram_monet.data.repository.PaletteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
	contextParam: Context,
	private val paletteRepository: PaletteRepository,
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

	private val _isUseDivider = MutableStateFlow(false)
	val isUseDivider: StateFlow<Boolean> = _isUseDivider.asStateFlow()

	private val sharedPreferences: SharedPreferences =
		contextParam.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)

	// -1 = system; matches PaletteEntity.id for saved palettes
	private val _selectedPaletteId = MutableStateFlow(
		sharedPreferences.getInt(Constants.SHARED_SELECTED_PALETTE_ID, -1)
	)
	val selectedPaletteId: StateFlow<Int> = _selectedPaletteId.asStateFlow()

	val allPalettes: StateFlow<List<PaletteEntity>> = paletteRepository.getAllPalettes()
		.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

	// null = system Monet; non-null = selected saved palette
	val selectedPalette: StateFlow<PaletteEntity?> = combine(
		_selectedPaletteId,
		allPalettes,
	) { id, palettes ->
		if (id == -1) null else palettes.find { it.id == id }
	}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

	fun selectPalette(paletteId: Int) {
		_selectedPaletteId.value = paletteId
		sharedPreferences.edit {
			putInt(Constants.SHARED_SELECTED_PALETTE_ID, paletteId)
		}
	}

	fun deletePalette(palette: PaletteEntity) {
		viewModelScope.launch {
			paletteRepository.deletePalette(palette)
			if (_selectedPaletteId.value == palette.id) {
				selectPalette(-1)
			}
		}
	}

	fun onShareTheme(context: Context, isTelegram: Boolean, isLight: Boolean) {
		val inputFileName = when {
			isTelegram && isLight -> Constants.INPUT_FILE_TELEGRAM_LIGHT
			isTelegram -> Constants.INPUT_FILE_TELEGRAM_DARK
			!isTelegram && isLight -> Constants.INPUT_FILE_TELEGRAM_X_LIGHT
			else -> Constants.INPUT_FILE_TELEGRAM_X_DARK
		}

		val outputFileName = when {
			isTelegram && isLight -> Constants.OUTPUT_FILE_TELEGRAM_LIGHT
			isTelegram && !isAmoled.value -> Constants.OUTPUT_FILE_TELEGRAM_DARK
			isTelegram -> Constants.OUTPUT_FILE_TELEGRAM_AMOLED
			!isTelegram && isLight -> Constants.OUTPUT_FILE_TELEGRAM_X_LIGHT
			!isTelegram && !isAmoled.value -> Constants.OUTPUT_FILE_TELEGRAM_X_DARK
			else -> Constants.OUTPUT_FILE_TELEGRAM_X_AMOLED
		}

		val palette = selectedPalette.value
		val customSeedColor = palette?.argbColor ?: 0
		val customScheme = palette?.let { ColorScheme.fromName(it.scheme) } ?: ColorScheme.TONAL_SPOT

		CoroutineScope(Dispatchers.IO).launch {
			createTheme(
				context = context,
				isTelegram = isTelegram,
				isAmoled = isAmoled.value,
				isGradient = isGradient.value,
				isAvatarGradient = isAvatarGradient.value,
				isNicknameColorful = isNicknameColorful.value,
				isAlterOutColor = isAlterOutColor.value,
				isUseDivider = isUseDivider.value,
				inputFileName = inputFileName,
				outputFileName = outputFileName,
				customSeedColor = customSeedColor,
				customScheme = customScheme,
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
			Constants.SHARED_USE_USE_DIVIDER -> _isUseDivider.value = value
		}
		sharedPreferences.edit { putBoolean(settings, value) }
	}

	private fun getSettings() {
		_isAmoled.value = sharedPreferences.getBoolean(Constants.SHARED_IS_AMOLED, false)
		_isGradient.value = sharedPreferences.getBoolean(Constants.SHARED_USE_GRADIENT, false)
		_isAvatarGradient.value = sharedPreferences.getBoolean(Constants.SHARED_USE_GRADIENT_AVATARS, false)
		_isNicknameColorful.value = sharedPreferences.getBoolean(Constants.SHARED_USE_COLORFUL_NICKNAME, true)
		_isAlterOutColor.value = sharedPreferences.getBoolean(Constants.SHARED_USE_OLD_CHAT_STYLE, true)
		_isUseDivider.value = sharedPreferences.getBoolean(Constants.SHARED_USE_USE_DIVIDER, false)
	}
}
