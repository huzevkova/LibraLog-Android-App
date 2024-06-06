package com.example.vamzaplikacia.data

import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Kniha] from a given data source.
 */
interface KnihyRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Kniha>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Kniha?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(kniha: Kniha)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(kniha: Kniha)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(kniha: Kniha)
}
