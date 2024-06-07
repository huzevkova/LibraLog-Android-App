package com.example.vamzaplikacia.data

import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import kotlinx.coroutines.flow.Flow

interface PolickyRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<PolickaKniznice>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<PolickaKniznice?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(policka: PolickaKniznice)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(policka: PolickaKniznice)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(policka: PolickaKniznice)
}
