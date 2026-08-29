package com.adnan.colormate

import android.app.Application
import com.adnan.colormate.data.AppDatabase
import com.adnan.colormate.data.ColorEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColorMateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        preloadDatabase()
    }

    private fun preloadDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val jsonString = assets.open("colors.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<ColorEntity>>() {}.type
            val colors: List<ColorEntity> = Gson().fromJson(jsonString, listType)
            db.colorDao().insertAll(colors)
        }
    }
}
