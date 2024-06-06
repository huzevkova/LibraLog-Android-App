package com.example.vamzaplikacia.data
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import kotlinx.coroutines.flow.Flow

class OfflineKniznicaRepository(private val knihaDao: KnihaDAO) : KnihyRepository {
    override fun getAllItemsStream(): Flow<List<Kniha>> = knihaDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Kniha?> = knihaDao.getItem(id)

    override suspend fun insertItem(kniha: Kniha) = knihaDao.insert(kniha)

    override suspend fun deleteItem(kniha: Kniha) = knihaDao.delete(kniha)

    override suspend fun updateItem(kniha: Kniha) = knihaDao.update(kniha)
}


class OfflineAutoriRepository(private val autorDao: AutorDAO) : AutoriRepository {
    override fun getAllItemsStream(): Flow<List<Autor>> = autorDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Autor?> = autorDao.getItem(id)

    override suspend fun insertItem(autor: Autor) = autorDao.insert(autor)

    override suspend fun deleteItem(autor: Autor) = autorDao.delete(autor)

    override suspend fun updateItem(autor: Autor) = autorDao.update(autor)
}

