package com.example.vamzaplikacia.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.Flow

@Dao
interface KnihaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(kniha: Kniha)

    @Update
    suspend fun update(kniha: Kniha)

    @Delete
    suspend fun delete(kniha: Kniha)

    @Query("SELECT * from knihy WHERE id_knihy = :id")
    fun getItem(id: Int): Flow<Kniha>

    @Query("SELECT * from knihy ORDER BY nazov ASC")
    fun getAllItems(): Flow<List<Kniha>>
}