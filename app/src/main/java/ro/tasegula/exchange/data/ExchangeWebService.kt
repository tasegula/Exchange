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

    fun getRates(currency: Currency): Single<ExchangeRate> = api.getRates(currency.name)

    interface Api {
        @GET("latest")
        fun getRates(@Query("base") base: String): Single<ExchangeRate>
    }

    companion object {
        private const val BASE_URL = "https://revolut.duckdns.org/"
    }
}
