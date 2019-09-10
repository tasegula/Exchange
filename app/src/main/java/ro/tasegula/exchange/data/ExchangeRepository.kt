package ro.tasegula.exchange.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ro.tasegula.exchange.core.AppPreferences
import ro.tasegula.exchange.utils.neverDispose
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepository
@Inject constructor(private val webService: ExchangeWebService,
                    private val appPreferences: AppPreferences,
                    private val exchangeDao: ExchangeDao) {

    private val baseRateSubject: BehaviorSubject<ExchangeRate> =
        BehaviorSubject.createDefault(appPreferences.baseRate)

    var baseRate: ExchangeRate
        get() = baseRateSubject.value ?: appPreferences.baseRate
        set(value) {
            appPreferences.baseRate = value

            // TODO: why do we need this to be run on io()
            Completable.complete()
                .observeOn(Schedulers.io())
                .subscribe {
                    baseRateSubject.onNext(value)
                }
                .neverDispose()
        }

    init {
        Observables.combineLatest(Observable.interval(1, TimeUnit.SECONDS),
                                  baseRateSubject.hide())
            .flatMapSingle { fetchRates(it.second) }
            .flatMapCompletable { saveRates(it) }
            .onErrorResumeNext {
                Timber.e(it, "Error")
                Completable.complete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
            .neverDispose()
    }

    /**
     * @return an [Observable] of the list of [ExchangeRate] that never fails
     */
    fun ratesDb(): Observable<List<ExchangeRate>> =
        exchangeDao.loadAll()
            .subscribeOn(Schedulers.computation())

    /**
     * *Scheduler:*
     * operates by default on [Schedulers.io()]
     *
     * @return the list of exchange rates for the given currency
     */
    private fun fetchRates(baseRate: ExchangeRate): Single<List<ExchangeRate>> =
        webService.getRates(baseRate.currency).map { it.rates }

    /**
     * Returns a [Completable] that never fails.
     */
    private fun saveRates(list: List<ExchangeRate>): Completable {
        return exchangeDao.saveAll(list)
            .onErrorResumeNext { Completable.complete() }
    }
}
