package com.c3r5b8.telegram_monet.data.repository

import com.c3r5b8.telegram_monet.data.local.dao.PaletteDao
import com.c3r5b8.telegram_monet.data.local.entity.PaletteEntity
import kotlinx.coroutines.flow.Flow

class PaletteRepository(private val paletteDao: PaletteDao) {

    fun getAllPalettes(): Flow<List<PaletteEntity>> = paletteDao.getAllPalettes()

    suspend fun getPaletteById(id: Int): PaletteEntity? = paletteDao.getPaletteById(id)

    suspend fun insertPalette(palette: PaletteEntity) = paletteDao.insertPalette(palette)

    suspend fun deletePalette(palette: PaletteEntity) = paletteDao.deletePalette(palette)

    suspend fun updatePalette(palette: PaletteEntity) = paletteDao.updatePalette(palette)
}
