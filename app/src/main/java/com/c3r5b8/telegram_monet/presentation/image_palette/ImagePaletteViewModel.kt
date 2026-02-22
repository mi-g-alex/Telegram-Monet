package com.c3r5b8.telegram_monet.presentation.image_palette

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3r5b8.telegram_monet.common.ColorScheme
import com.c3r5b8.telegram_monet.data.local.entity.PaletteEntity
import com.c3r5b8.telegram_monet.data.repository.PaletteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class ImagePaletteUiState {
    object Idle : ImagePaletteUiState()
    object Loading : ImagePaletteUiState()
    object ColorsReady : ImagePaletteUiState()
}

class ImagePaletteViewModel(
    private val paletteRepository: PaletteRepository,
    private val imageProcessor: ImageProcessor,
    private val paletteId: Int = -1,
) : ViewModel() {

    val isEditMode: Boolean = paletteId != -1

    private val _uiState = MutableStateFlow<ImagePaletteUiState>(ImagePaletteUiState.Idle)
    val uiState: StateFlow<ImagePaletteUiState> = _uiState.asStateFlow()

    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> = _imageBitmap.asStateFlow()

    private val _extractedColors = MutableStateFlow<List<Int>>(emptyList())
    val extractedColors: StateFlow<List<Int>> = _extractedColors.asStateFlow()

    private val _selectedColorIndex = MutableStateFlow<Int?>(null)
    val selectedColorIndex: StateFlow<Int?> = _selectedColorIndex.asStateFlow()

    private val _manualColor = MutableStateFlow<Int?>(null)
    val manualColor: StateFlow<Int?> = _manualColor.asStateFlow()

    private val _selectedScheme = MutableStateFlow(ColorScheme.TONAL_SPOT)
    val selectedScheme: StateFlow<ColorScheme> = _selectedScheme.asStateFlow()

    // Non-null when edit mode is ready (palette loaded)
    private val _initialName = MutableStateFlow<String?>(null)
    val initialName: StateFlow<String?> = _initialName.asStateFlow()

    private var editingPalette: PaletteEntity? = null

    init {
        if (isEditMode) {
            viewModelScope.launch {
                val palette = withContext(Dispatchers.IO) {
                    paletteRepository.getPaletteById(paletteId)
                } ?: return@launch
                editingPalette = palette
                _manualColor.value = palette.argbColor
                _selectedScheme.value = ColorScheme.fromName(palette.scheme)
                _initialName.value = palette.name
            }
        }
    }

    fun processImage(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = ImagePaletteUiState.Loading
            _selectedColorIndex.value = null
            _manualColor.value = null
            try {
                val (bitmap, colors) = imageProcessor.processImage(uri)
                _imageBitmap.value = bitmap
                _extractedColors.value = colors
                _uiState.value = ImagePaletteUiState.ColorsReady
            } catch (e: Exception) {
                _uiState.value = ImagePaletteUiState.Idle
            }
        }
    }

    fun selectColor(index: Int) {
        _selectedColorIndex.value = index
        _manualColor.value = null
    }

    fun setManualColor(argb: Int) {
        _imageBitmap.value?.recycle()
        _imageBitmap.value = null
        _extractedColors.value = emptyList()
        _selectedColorIndex.value = null
        _manualColor.value = argb
        _uiState.value = ImagePaletteUiState.Idle
    }

    fun setScheme(scheme: ColorScheme) {
        _selectedScheme.value = scheme
    }

    fun reset() {
        _imageBitmap.value?.recycle()
        _imageBitmap.value = null
        _extractedColors.value = emptyList()
        _selectedColorIndex.value = null
        _manualColor.value = null
        _uiState.value = ImagePaletteUiState.Idle
    }

    fun savePalette(name: String) {
        viewModelScope.launch {
            if (isEditMode) {
                val base = editingPalette ?: return@launch
                paletteRepository.updatePalette(
                    base.copy(name = name, scheme = _selectedScheme.value.name)
                )
            } else {
                val argb = _manualColor.value
                    ?: _selectedColorIndex.value?.let { _extractedColors.value.getOrNull(it) }
                    ?: return@launch
                paletteRepository.insertPalette(
                    PaletteEntity(
                        name = name,
                        argbColor = argb,
                        scheme = _selectedScheme.value.name,
                    )
                )
            }
        }
    }
}
