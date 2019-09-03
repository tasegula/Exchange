package ro.tasegula.exchange

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeRepository
import ro.tasegula.exchange.data.ExchangeWebService
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    protected lateinit var repository: ExchangeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository.getRates(Currency.EUR)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            findViewById<TextView>(R.id.text).text = it.rates.toString()
                        },
                        {
                            findViewById<TextView>(R.id.text).text = it.localizedMessage
                        })


    }
}
