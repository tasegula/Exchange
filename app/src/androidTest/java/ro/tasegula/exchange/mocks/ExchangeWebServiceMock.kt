package ro.tasegula.exchange.mocks

import io.reactivex.Single
import org.mockito.Mockito
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeBase
import ro.tasegula.exchange.data.ExchangeRate
import ro.tasegula.exchange.data.ExchangeWebService
import ro.tasegula.exchange.utils.MockHelper
import ro.tasegula.exchange.utils.anyk
import ro.tasegula.exchange.utils.on

class ExchangeWebServiceMock {

    var base: ExchangeBase = ratesEur
    var latestBase: ExchangeBase = base
        private set

    fun baseFor(exchangeRate: ExchangeRate): ExchangeBase = base.changeForExchangeRate(exchangeRate)

    fun mocked(): ExchangeWebService {
        val ws = Mockito.mock(ExchangeWebService::class.java)

        on(ws.getRates(anyk<ExchangeRate>())).thenAnswer {
            val exchangeRate = it.arguments[0] as ExchangeRate

            latestBase = base.changeForExchangeRate(exchangeRate)
            Single.just(latestBase)
        }

        on(ws.getRates(anyk<Currency>())).thenAnswer {
            val currency = it.arguments[0] as Currency

            latestBase = base.changeForExchangeRate(ExchangeRate(currency, 1f))
            Single.just(latestBase)
        }

        return ws
    }

    companion object {
        val ratesEur: ExchangeBase = MockHelper.parseFromAssets("exchange/200_EUR.json")

        fun ExchangeBase.changeForExchangeRate(exchangeRate: ExchangeRate): ExchangeBase {
            if (this.base == exchangeRate.currency) return this

            // find wanted currency rate
            val rate: ExchangeRate = this.rates.firstOrNull { it.currency == exchangeRate.currency }
                    ?: throw IllegalStateException("Couldn't find currency ${exchangeRate.currency}")

            val remainingRates: List<ExchangeRate> = this.rates.minus(rate)
                .map { it.copy(amount = it.amount / rate.amount) }

            return ExchangeBase(
                base = exchangeRate.currency,
                date = this.date,
                map = remainingRates.map { it.currency to it.amount }.toMap())
        }
    }
}
