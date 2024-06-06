package com.example.vamzaplikacia.data

import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Autor] from a given data source.
 */
interface AutoriRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Autor>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Autor?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(autor: Autor)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(autor: Autor)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(autor: Autor)
}
