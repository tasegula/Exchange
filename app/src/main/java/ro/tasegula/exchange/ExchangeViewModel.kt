package ro.tasegula.exchange

import androidx.databinding.Bindable
import io.reactivex.android.schedulers.AndroidSchedulers
import ro.tasegula.exchange.arch.ObservableViewModel
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeRepository
import javax.inject.Inject

class ExchangeViewModel
@Inject constructor(repository: ExchangeRepository) : ObservableViewModel() {

    init {
        repository.getRates(Currency.EUR)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            values = it.rates.toString()
                        },
                        {
                            values = it.localizedMessage
                        })
    }

    @get:Bindable
    var values: String = ""
        private set(value) {
            field = value
            notifyPropertyChanged(BR.values)
        }
}
