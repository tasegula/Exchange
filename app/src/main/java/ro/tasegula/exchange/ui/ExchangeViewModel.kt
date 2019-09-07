package ro.tasegula.exchange.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import ro.tasegula.exchange.arch.ObservableViewModel
import ro.tasegula.exchange.arch.list.ItemViewModel
import ro.tasegula.exchange.core.StringResources
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeRepository
import javax.inject.Inject

class ExchangeViewModel
@Inject constructor(private val stringResources: StringResources,
                    repository: ExchangeRepository) : ObservableViewModel() {

    private var baseCurrency: Currency = Currency.EUR
    private var baseAmount: Double = 1.0

    private var rates: Map<Currency, Double> = mapOf()

    init {
        repository.getRates(baseCurrency)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            makeList(it.rates.plus(Pair(baseCurrency, baseAmount)))
                        },
                        {
                        })
    }

    val adapter: ExchangeAdapter = ExchangeAdapter()

    private fun makeList(map: Map<Currency, Double>) {
        rates = map

        val list: List<RateItemViewModel> =
                rates.map { getItemViewModel(it.key, it.value * baseAmount) }
        adapter.submitList(list.sortedBy { it.currency != baseCurrency })
    }

    private fun getItemViewModel(currency: Currency, value: Double): RateItemViewModel =
            RateItemViewModel(stringResources, currency, value, this::update)

    private fun update(currency: Currency, from: Double, to: Double) {
        baseAmount = baseAmount * to / from
        baseCurrency = currency
        makeList(rates)
    }
}

class RateItemViewModel(stringResources: StringResources,
                        val currency: Currency,
                        private val rate: Double,
                        private val update: (currency: Currency, from: Double, to: Double) -> Unit) : ItemViewModel {

    val icon: Int = currency.icon
    val name: String = currency.name
    val description: String = stringResources.getString(currency.description)

    var amount: String = "%.2f".format(rate)
        set(value) {
            if (field == value) return
            update(currency, rate, value.toDouble())
            field = value
        }

    override fun isSameItemAs(other: ItemViewModel): Boolean =
            other is RateItemViewModel && this.currency == other.currency

    override fun isSameContentAs(other: ItemViewModel): Boolean =
            other is RateItemViewModel && this.amount == other.amount
}
