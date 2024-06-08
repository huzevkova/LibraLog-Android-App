package com.example.vamzaplikacia.data

import android.content.Context
import com.example.vamzaplikacia.data.autoriData.AutoriRepository
import com.example.vamzaplikacia.data.knihyData.KnihyRepository
import com.example.vamzaplikacia.data.polickyData.PolickyRepository

interface AppContainer {
    val knihyRepository: KnihyRepository
    val autoriRepository: AutoriRepository
    val polickyRepository: PolickyRepository
}

/**
 * Implementácia AppContainer ktorá sprístupňuje offline repozitáre
 *
 * @param context kontext v ktorom sa repozitár otvorí
 */
class AppDataContainer(private val context: Context) : AppContainer {

    override val knihyRepository: KnihyRepository by lazy {
        OfflineKniznicaRepository(KniznicaDatabase.getDatabase(context).knihaDAO())
    }

    override val autoriRepository: AutoriRepository by lazy {
        OfflineAutoriRepository(KniznicaDatabase.getDatabase(context).autoriDAO())
    }

    override val polickyRepository: PolickyRepository by lazy {
        OfflinePolickyRepository(KniznicaDatabase.getDatabase(context).polickyDAO())
    }
}
