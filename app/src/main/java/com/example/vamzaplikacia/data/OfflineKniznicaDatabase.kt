package com.example.vamzaplikacia.data
import com.example.vamzaplikacia.data.autoriData.AutorDAO
import com.example.vamzaplikacia.data.autoriData.AutoriRepository
import com.example.vamzaplikacia.data.knihyData.KnihaDAO
import com.example.vamzaplikacia.data.knihyData.KnihyRepository
import com.example.vamzaplikacia.data.polickyData.PolickaDAO
import com.example.vamzaplikacia.data.polickyData.PolickyRepository
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice
import kotlinx.coroutines.flow.Flow

/**
 * Offline repozitár kníh
 *
 * @param knihaDao
 */
class OfflineKniznicaRepository(private val knihaDao: KnihaDAO) : KnihyRepository {
    override fun getAllItemsStream(): Flow<List<Kniha>> = knihaDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Kniha?> = knihaDao.getItem(id)

    override suspend fun insertItem(kniha: Kniha) = knihaDao.insert(kniha)

    override suspend fun deleteItem(kniha: Kniha) = knihaDao.delete(kniha)

    override suspend fun updateItem(kniha: Kniha) = knihaDao.update(kniha)
}

/**
 * Offline repozitár autorov
 *
 * @param autorDao
 */
class OfflineAutoriRepository(private val autorDao: AutorDAO) : AutoriRepository {
    override fun getAllItemsStream(): Flow<List<Autor>> = autorDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Autor?> = autorDao.getItem(id)

    override suspend fun insertItem(autor: Autor) = autorDao.insert(autor)

    override suspend fun deleteItem(autor: Autor) = autorDao.delete(autor)

    override suspend fun updateItem(autor: Autor) = autorDao.update(autor)
}

/**
 * Offline repozitár poličiek (zoznamov)
 *
 * @param polickaDAO
 */
class OfflinePolickyRepository(private val polickaDAO: PolickaDAO) : PolickyRepository {
    override fun getAllItemsStream(): Flow<List<PolickaKniznice>> = polickaDAO.getAllItems()

    override fun getItemStream(id: Int): Flow<PolickaKniznice?> = polickaDAO.getItem(id)

    override suspend fun insertItem(policka: PolickaKniznice) = polickaDAO.insert(policka)

    override suspend fun deleteItem(policka: PolickaKniznice) = polickaDAO.delete(policka)

    override suspend fun updateItem(policka: PolickaKniznice) = polickaDAO.update(policka)
}
