package ro.tasegula.exchange.mocks

import io.reactivex.Single
import org.mockito.Mockito
import ro.tasegula.exchange.data.ExchangeBase
import ro.tasegula.exchange.data.ExchangeRate
import ro.tasegula.exchange.data.ExchangeWebService
import ro.tasegula.exchange.utils.MockHelper
import ro.tasegula.exchange.utils.anyk
import ro.tasegula.exchange.utils.on

class ExchangeWebServiceMock {

    var base: ExchangeBase = MockHelper.parseFromAssets("exchange/rates_200.json")

    var rates: List<ExchangeRate> = base.rates

    fun mocked(): ExchangeWebService {
        val ws = Mockito.mock(ExchangeWebService::class.java)

        on(ws.getRates(anyk())).thenAnswer {
            Single.just(base)
        }

        return ws
    }
}
