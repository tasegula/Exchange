package ro.tasegula.exchange.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ro.tasegula.exchange.utils.neverDispose
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepository
@Inject constructor(private val webService: ExchangeWebService,
                    private val exchangeDao: ExchangeDao) {

    init {
        Observable.interval(1, TimeUnit.SECONDS)
                .flatMapSingle { fetchRates() }
                .flatMapCompletable { saveRates(it) }
                .onErrorResumeNext { Completable.complete() }
                .subscribe {}
                .neverDispose()

    }

    /**
     * @return an [Observable] of the list of [ExchangeRate] that never fails
     */
    fun ratesDb(): Observable<List<ExchangeRate>> =
            exchangeDao.loadAll()
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.computation())

    /**
     * *Scheduler:*
     * operates by default on [Schedulers.io()]
     *
     * @return the list of exchange rates for the given currency
     */
    private fun fetchRates(): Single<List<ExchangeRate>> =
            webService.getRates(baseCurrency).map { it.rates }


    /**
     * Returns a [Completable] that never fails.
     */
    private fun saveRates(list: List<ExchangeRate>): Completable {
        return exchangeDao.saveAll(list)
                .onErrorResumeNext { Completable.complete() }
    }

    companion object {
        val baseCurrency: Currency = Currency.EUR
    }
}
