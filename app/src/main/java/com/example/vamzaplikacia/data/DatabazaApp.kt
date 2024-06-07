package com.example.vamzaplikacia.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha
import com.example.vamzaplikacia.logika.knihy.PolickaKniznice

@Database(entities = [Kniha::class, Autor::class, PolickaKniznice::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KniznicaDatabase : RoomDatabase() {
    abstract fun knihaDAO() : KnihaDAO
    abstract fun autoriDAO() : AutorDAO
    abstract fun polickyDAO() : PolickaDAO

    companion object {
        @Volatile
        private var Instance: KniznicaDatabase? = null

        fun getDatabase(context: Context): KniznicaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, KniznicaDatabase::class.java, "app_database")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE knihy ADD COLUMN policka TEXT NOT NULL DEFAULT 'Všetko'")

        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `policky` (
                `id_policky` INTEGER NOT NULL,
                `nazov` TEXT NOT NULL,
                PRIMARY KEY(`id_policky`)
            )
        """)
    }
}