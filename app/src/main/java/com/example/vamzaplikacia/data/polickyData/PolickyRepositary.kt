package com.example.vamzaplikacia.data.polickyData

import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import kotlinx.coroutines.flow.Flow

interface PolickyRepository {

    fun getAllItemsStream(): Flow<List<PolickaKniznice>>

    fun getItemStream(id: Int): Flow<PolickaKniznice?>

    suspend fun insertItem(policka: PolickaKniznice)

    suspend fun deleteItem(policka: PolickaKniznice)

    suspend fun updateItem(policka: PolickaKniznice)
}
