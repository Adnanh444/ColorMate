package com.adnan.colormate.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_library")
data class ColorEntity(
    @PrimaryKey val id: String,
    val name_en: String,
    val name_bn: String,
    val hex: String,
    val rgbR: Int,
    val rgbG: Int,
    val rgbB: Int,
    val category: String,
    val tone: String,
    val tags: String,
    val description_bn: String,
    val isCustom: Boolean = false
)
