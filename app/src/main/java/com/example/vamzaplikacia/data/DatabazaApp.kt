package com.example.vamzaplikacia.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vamzaplikacia.logika.knihy.Autor
import com.example.vamzaplikacia.logika.knihy.Kniha

@Database(entities = [Kniha::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KniznicaDatabase : RoomDatabase() {
    abstract fun knihaDAO() : KnihaDAO
    companion object {
        @Volatile
        private var Instance: KniznicaDatabase? = null

        fun getDatabase(context: Context): KniznicaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, KniznicaDatabase::class.java, "book_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}


@Database(entities = [Autor::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AutoriDatabase : RoomDatabase() {
    abstract fun autoriDAO() : AutorDAO
    companion object {
        @Volatile
        private var Instance: AutoriDatabase? = null

        fun getDatabase(context: Context): AutoriDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AutoriDatabase::class.java, "author_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

