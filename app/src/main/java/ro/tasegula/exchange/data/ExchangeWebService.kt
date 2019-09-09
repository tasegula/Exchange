package ro.tasegula.exchange.data

import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeWebService
@Inject constructor() {

    private val api: Api

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }

    /**
     * @return the [ExchangeBase] with a list that contains [ExchangeRate]
     * according to the given amount
     */
    fun getRates(rate: ExchangeRate): Single<ExchangeBase> =
        api.getRates(rate.currency.name, rate.amount)

    /**
     * @return the [ExchangeBase] with a list that contains [ExchangeRate]
     * as if the amount is 1.00
     */
    fun getRates(currency: Currency): Single<ExchangeBase> =
        api.getRates(currency.name)

    interface Api {
        @GET("latest")
        fun getRates(@Query("base") base: String,
                     @Query("amount") amount: Float? = null): Single<ExchangeBase>
    }

    companion object {
        private const val BASE_URL = "https://revolut.duckdns.org/"
    }
}
