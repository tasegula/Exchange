package ro.tasegula.exchange.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ExchangeRepository(private val webService: ExchangeWebService) {

    /**
     * *Scheduler:*
     * operates by default on [Schedulers.io()]
     *
     * @return the list of exchange rates for the given currency
     */
    fun getRates(currency: Currency): Single<ExchangeRate> =
        webService.getRates(currency).subscribeOn(Schedulers.io())

}