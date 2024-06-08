package com.example.vamzaplikacia.data.polickyData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import kotlinx.coroutines.flow.Flow

/**
 * DAO pre poličky (zoznamy)
 */
@Dao
interface PolickaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(policka: PolickaKniznice)

    @Update
    suspend fun update(policka: PolickaKniznice)

    @Delete
    suspend fun delete(policka: PolickaKniznice)

    @Query("SELECT * from policky WHERE id_policky = :id")
    fun getItem(id: Int): Flow<PolickaKniznice>

    @Query("SELECT * from policky ORDER BY nazov ASC")
    fun getAllItems(): Flow<List<PolickaKniznice>>
}