package ro.tasegula.exchange.data

import androidx.room.*

@Dao
abstract class ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(data: ExchangeRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveAll(data: List<ExchangeRate>)

    @Delete
    abstract fun delete(data: ExchangeRate)

    @Delete
    abstract fun delete(data: List<ExchangeRate>)

    @Query("DELETE FROM ExchangeRate")
    abstract fun deleteAll()

    @Query("SELECT * from ExchangeRate")
    abstract fun loadAll(): List<ExchangeRate>

    @Transaction
    open fun replace(itemsToCreateOrUpdate: List<ExchangeRate>, itemsToDelete: List<ExchangeRate>) {
        saveAll(itemsToCreateOrUpdate)
        delete(itemsToDelete)
    }
}
