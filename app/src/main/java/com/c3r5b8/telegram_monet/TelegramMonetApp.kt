package com.c3r5b8.telegram_monet

import android.app.Application
import com.c3r5b8.telegram_monet.di.appModule
import com.c3r5b8.telegram_monet.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TelegramMonetApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TelegramMonetApp)
            modules(
                appModule,
                databaseModule
            )
        }
    }
}
