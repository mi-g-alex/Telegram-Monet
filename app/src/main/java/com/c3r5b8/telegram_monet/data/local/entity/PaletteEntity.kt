package com.c3r5b8.telegram_monet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palettes")
data class PaletteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val argbColor: Int,
    val scheme: String = "TONAL_SPOT",
    val createdAt: Long = System.currentTimeMillis()
)
