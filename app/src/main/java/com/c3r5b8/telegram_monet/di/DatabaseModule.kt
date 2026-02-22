package com.c3r5b8.telegram_monet.di

import androidx.room.Room
import com.c3r5b8.telegram_monet.data.local.AppDatabase
import com.c3r5b8.telegram_monet.data.repository.PaletteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
			AppDatabase.DB_NAME,
        ).build()
    }

    single { get<AppDatabase>().paletteDao() }

    single { PaletteRepository(get()) }
}
