package com.example.vamzaplikacia.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val knihyRepository: KnihyRepository
    val autoriRepository: AutoriRepository
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
        OfflineAutoriRepository(AutoriDatabase.getDatabase(context).autoriDAO())
    }
}
