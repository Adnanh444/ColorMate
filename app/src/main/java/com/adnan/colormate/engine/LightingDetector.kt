package com.adnan.colormate.engine

enum class LightingStatus { NORMAL, TOO_DARK, OVEREXPOSED }

object LightingDetector {
    fun evaluateLighting(r: Int, g: Int, b: Int): LightingStatus {
        val brightness = (0.299 * r + 0.587 * g + 0.114 * b)
        return when {
            brightness < 40 -> LightingStatus.TOO_DARK
            brightness > 220 -> LightingStatus.OVEREXPOSED
            else -> LightingStatus.NORMAL
        }
    }
}
