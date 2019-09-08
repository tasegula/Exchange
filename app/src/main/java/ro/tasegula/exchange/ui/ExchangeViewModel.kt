package ro.tasegula.exchange.ui

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import ro.tasegula.exchange.arch.ObservableViewModel
import ro.tasegula.exchange.arch.list.ItemViewModel
import ro.tasegula.exchange.core.StringResources
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeRate
import ro.tasegula.exchange.data.ExchangeRepository
import javax.inject.Inject

class ExchangeViewModel
@Inject constructor(private val stringResources: StringResources,
                    repository: ExchangeRepository) : ObservableViewModel() {

    private var rates: List<ExchangeRate> = listOf()
    private var baseRate: ExchangeRate = ExchangeRate(Currency.EUR, 1.0)
    private var exchangeCurrency: Currency = Currency.EUR

    init {
        repository.getRates(baseRate.currency)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            baseRate = it.first()
                            makeList(it)
                        },
                        {
                        })
    }

    val adapter: ExchangeAdapter = ExchangeAdapter()

    private fun makeList(list: List<ExchangeRate>) {
        rates = list

        val result: List<RateItemViewModel> =
                rates.map { getItemViewModel(it.currency, it.amount * baseRate.amount) }
        adapter.submitList(result)
    }

    private fun getItemViewModel(currency: Currency, value: Double): RateItemViewModel =
            RateItemViewModel(stringResources, currency, value, this::update, this::updateCurrency)

    private fun update(currency: Currency, from: Double, to: Double) {
        if (exchangeCurrency != currency) return

        baseRate = baseRate.copy(amount = baseRate.amount * to / from)
        makeList(rates)
    }

    private fun updateCurrency(currency: Currency) {
        exchangeCurrency = currency
        makeList(rates.sortedBy { if (it.currency == currency) "" else it.currency.name })
    }
}

class RateItemViewModel(stringResources: StringResources,
                        val currency: Currency,
                        private val rate: Double,
                        private val update: (currency: Currency, from: Double, to: Double) -> Unit,
                        private val updateCurrency: (Currency) -> Unit) : ItemViewModel {

    val icon: Int = currency.icon
    val name: String = currency.name
    val description: String = stringResources.getString(currency.description)

    var amount: String = "%.2f".format(rate)
        set(value) {
            if (field == value)
                return
            update(currency, rate, value.toDouble())
            field = value
        }

    val onFocusChangeListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (hasFocus)
                updateCurrency(currency)
        }
    }

    override fun isSameItemAs(other: ItemViewModel): Boolean =
            other is RateItemViewModel && this.currency == other.currency

    override fun isSameContentAs(other: ItemViewModel): Boolean =
            other is RateItemViewModel && this.amount == other.amount
}
