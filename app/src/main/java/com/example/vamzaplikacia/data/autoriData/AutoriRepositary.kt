package com.example.vamzaplikacia.data.autoriData

import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.Flow

/**
 * Repozitár pre autorov
 */
interface AutoriRepository {

    fun getAllItemsStream(): Flow<List<Autor>>

    fun getItemStream(id: Int): Flow<Autor?>

    suspend fun insertItem(autor: Autor)

    suspend fun deleteItem(autor: Autor)

    suspend fun updateItem(autor: Autor)
}
