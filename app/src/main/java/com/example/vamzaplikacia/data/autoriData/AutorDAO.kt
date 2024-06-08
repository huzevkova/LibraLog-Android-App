package com.example.vamzaplikacia.data.autoriData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vamzaplikacia.logika.knihy.Autor
import kotlinx.coroutines.flow.Flow

@Dao
interface AutorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(autor: Autor)

    @Update
    suspend fun update(autor: Autor)

    @Delete
    suspend fun delete(autor: Autor)

    @Query("SELECT * from autori WHERE id_autora = :id")
    fun getItem(id: Int): Flow<Autor>

    @Query("SELECT * from autori ORDER BY meno ASC")
    fun getAllItems(): Flow<List<Autor>>
}