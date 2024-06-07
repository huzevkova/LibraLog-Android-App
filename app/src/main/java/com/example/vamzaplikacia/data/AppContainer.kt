package com.example.vamzaplikacia.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val knihyRepository: KnihyRepository
    val autoriRepository: AutoriRepository
    val polickyRepository: PolickyRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineKniznicaRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [KnihyRepository]
     */
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
