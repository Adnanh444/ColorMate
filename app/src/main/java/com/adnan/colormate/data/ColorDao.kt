package com.adnan.colormate.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM color_library")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(colors: List<ColorEntity>)

    @Query("SELECT * FROM color_library WHERE name_en LIKE '%' || :query || '%' OR name_bn LIKE '%' || :query || '%'")
    fun searchColors(query: String): Flow<List<ColorEntity>>
}
