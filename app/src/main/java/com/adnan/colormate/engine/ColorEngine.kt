package com.adnan.colormate.engine

import com.adnan.colormate.data.ColorEntity
import kotlin.math.pow
import kotlin.math.sqrt

object ColorEngine {

    fun rgbToHex(r: Int, g: Int, b: Int): String {
        return String.format("#%02X%02X%02X", r, g, b)
    }

    fun rgbToHsl(r: Int, g: Int, b: Int): FloatArray {
        val rf = r / 255f
        val gf = g / 255f
        val bf = b / 255f

        val max = maxOf(rf, gf, bf)
        val min = minOf(rf, gf, bf)
        val delta = max - min

        var h = 0f
        var s = 0f
        val l = (max + min) / 2f

        if (delta != 0f) {
            s = if (l < 0.5f) delta / (max + min) else delta / (2f - max - min)
            h = when (max) {
                rf -> (gf - bf) / delta + (if (gf < bf) 6 else 0)
                gf -> (bf - rf) / delta + 2
                else -> (rf - gf) / delta + 4
            }
            h *= 60f
        }
        return floatArrayOf(h, s * 100f, l * 100f)
    }

    fun rgbToHsv(r: Int, g: Int, b: Int): FloatArray {
        val hsv = FloatArray(3)
        android.graphics.Color.RGBToHSV(r, g, b, hsv)
        return hsv
    }

    fun rgbToLab(r: Int, g: Int, b: Int): DoubleArray {
        var varR = r / 255.0
        var varG = g / 255.0
        var varB = b / 255.0

        varR = if (varR > 0.04045) ((varR + 0.055) / 1.055).pow(2.4) else varR / 12.92
        varG = if (varG > 0.04045) ((varG + 0.055) / 1.055).pow(2.4) else varG / 12.92
        varB = if (varB > 0.04045) ((varB + 0.055) / 1.055).pow(2.4) else varB / 12.92

        varR *= 100
        varG *= 100
        varB *= 100

        val x = varR * 0.4124 + varG * 0.3576 + varB * 0.1805
        val y = varR * 0.2126 + varG * 0.7152 + varB * 0.0722
        val z = varR * 0.0193 + varG * 0.1192 + varB * 0.9505

        var varX = x / 95.047
        var varY = y / 100.000
        var varZ = z / 108.883

        varX = if (varX > 0.008856) varX.pow(1.0 / 3.0) else (7.787 * varX) + (16.0 / 116.0)
        varY = if (varY > 0.008856) varY.pow(1.0 / 3.0) else (7.787 * varY) + (16.0 / 116.0)
        varZ = if (varZ > 0.008856) varZ.pow(1.0 / 3.0) else (7.787 * varZ) + (16.0 / 116.0)

        val l = (116 * varY) - 16
        val a = 500 * (varX - varY)
        val bVal = 200 * (varY - varZ)

        return doubleArrayOf(l, a, bVal)
    }

    fun calculateDeltaE(lab1: DoubleArray, lab2: DoubleArray): Double {
        return sqrt(
            (lab1[0] - lab2[0]).pow(2) +
            (lab1[1] - lab2[1]).pow(2) +
            (lab1[2] - lab2[2]).pow(2)
        )
    }

    fun findClosestMatch(targetR: Int, targetG: Int, targetB: Int, database: List<ColorEntity>): Pair<ColorEntity, Double>? {
        if (database.isEmpty()) return null
        val targetLab = rgbToLab(targetR, targetG, targetB)

        var bestMatch: ColorEntity? = null
        var minDistance = Double.MAX_VALUE

        for (color in database) {
            val dbLab = rgbToLab(color.rgbR, color.rgbG, color.rgbB)
            val distance = calculateDeltaE(targetLab, dbLab)
            if (distance < minDistance) {
                minDistance = distance
                bestMatch = color
            }
        }

        val confidence = (100.0 - (minDistance * 1.5)).coerceIn(0.0, 99.0)
        return bestMatch?.let { Pair(it, confidence) }
    }
}
