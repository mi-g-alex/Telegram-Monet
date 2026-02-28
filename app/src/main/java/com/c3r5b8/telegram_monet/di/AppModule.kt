package com.c3r5b8.telegram_monet.di

import com.c3r5b8.telegram_monet.presentation.image_palette.ImagePaletteViewModel
import com.c3r5b8.telegram_monet.presentation.image_palette.ImageProcessor
import com.c3r5b8.telegram_monet.presentation.main_screen.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ImageProcessor(androidContext()) }
    viewModel { MainScreenViewModel(androidContext(), get()) }
    viewModel { (paletteId: Int) -> ImagePaletteViewModel(get(), get(), paletteId) }
}
