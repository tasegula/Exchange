package ro.tasegula.exchange.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepository
@Inject constructor(private val webService: ExchangeWebService) {

    /**
     * *Scheduler:*
     * operates by default on [Schedulers.io()]
     *
     * @return the list of exchange rates for the given currency
     */
    fun getRates(currency: Currency): Single<List<ExchangeRate>> =
            webService.getRates(currency).map { it.rates }.subscribeOn(Schedulers.io())
}
