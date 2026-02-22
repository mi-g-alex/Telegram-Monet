package com.c3r5b8.telegram_monet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.c3r5b8.telegram_monet.data.local.entity.PaletteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaletteDao {
    @Query("SELECT * FROM palettes ORDER BY createdAt DESC")
    fun getAllPalettes(): Flow<List<PaletteEntity>>

    @Query("SELECT * FROM palettes WHERE id = :id LIMIT 1")
    suspend fun getPaletteById(id: Int): PaletteEntity?

    @Insert
    suspend fun insertPalette(palette: PaletteEntity)

    @Delete
    suspend fun deletePalette(palette: PaletteEntity)

    @Update
    suspend fun updatePalette(palette: PaletteEntity)
}
