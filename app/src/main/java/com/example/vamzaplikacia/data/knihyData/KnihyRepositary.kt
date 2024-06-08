package com.example.vamzaplikacia.data.knihyData

import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.Flow


interface KnihyRepository {

    fun getAllItemsStream(): Flow<List<Kniha>>

    fun getItemStream(id: Int): Flow<Kniha?>

    suspend fun insertItem(kniha: Kniha)

    suspend fun deleteItem(kniha: Kniha)

    suspend fun updateItem(kniha: Kniha)
}
