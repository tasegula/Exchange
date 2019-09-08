package ro.tasegula.exchange.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
abstract class ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(data: ExchangeRate): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveAll(data: List<ExchangeRate>): Completable

    @Delete
    abstract fun delete(data: ExchangeRate): Completable

    @Delete
    abstract fun delete(data: List<ExchangeRate>): Completable

    @Query("DELETE FROM ExchangeRate")
    abstract fun deleteAll(): Completable

    @Query("SELECT * from ExchangeRate")
    abstract fun loadAll(): Observable<List<ExchangeRate>>

    @Query("SELECT * from ExchangeRate")
    abstract fun fetchAll(): Maybe<List<ExchangeRate>>

    @Transaction
    open fun replace(itemsToCreateOrUpdate: List<ExchangeRate>, itemsToDelete: List<ExchangeRate>) {
        saveAll(itemsToCreateOrUpdate)
        delete(itemsToDelete)
    }
}
