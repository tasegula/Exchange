package ro.tasegula.exchange.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ro.tasegula.exchange.data.ExchangeDao
import ro.tasegula.exchange.data.ExchangeRate

private const val CURRENT_VERSION = 1

@Database(entities = [ExchangeRate::class],
          version = CURRENT_VERSION)
abstract class ExchangeDatabase : RoomDatabase() {

    abstract val exchangeDao: ExchangeDao

    companion object {
        fun build(context: Context): ExchangeDatabase {
            return Room.databaseBuilder(context, ExchangeDatabase::class.java, "exchange.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}