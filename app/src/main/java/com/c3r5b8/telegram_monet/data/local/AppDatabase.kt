package com.c3r5b8.telegram_monet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.c3r5b8.telegram_monet.data.local.dao.PaletteDao
import com.c3r5b8.telegram_monet.data.local.entity.PaletteEntity

@Database(
    entities = [PaletteEntity::class],
    version = 1,
    exportSchema = true,
	autoMigrations = [],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paletteDao(): PaletteDao

	companion object {
		const val DB_NAME = "tg_monet_db"
	}
}
