package ro.tasegula.exchange.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import ro.tasegula.exchange.core.StringResources
import ro.tasegula.exchange.core.arch.ObservableViewModel
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeRate
import ro.tasegula.exchange.data.ExchangeRepository
import ro.tasegula.exchange.utils.neverDispose
import timber.log.Timber
import javax.inject.Inject

class ExchangeViewModel
@Inject constructor(private val stringResources: StringResources,
                    repository: ExchangeRepository) : ObservableViewModel() {

    private var currencies: List<Currency> = listOf()
    private var rates: List<ExchangeRate> = listOf()
    private var ratesVM: List<ExchangeItemViewModel> = listOf()

    private var baseRate: ExchangeRate = ExchangeRate(Currency.EUR, 1.0)
    private var exchangeCurrency: Currency = Currency.EUR

    init {
        repository.ratesDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    generateList(it)
                }
                .neverDispose()
    }

    val adapter: ExchangeAdapter = ExchangeAdapter()

    private fun generateList(list: List<ExchangeRate>) {
        // same list, just updateAmount values
        if (list.map { it.currency }.minus(currencies).isEmpty()) {
            val ratesMap: Map<Currency, Double> = list.map { it.currency to it.amount }.toMap()

            ratesVM.forEach { vm ->
                val x = (ratesMap[vm.currency] ?: 0.0) * baseRate.amount
                if (vm.currency == Currency.AUD) {
                    Timber.d("update from %s to %s (map: %s, base: %s)",
                            vm.amount, x,
                            ratesMap[vm.currency], baseRate.amount)
                }
                vm.amount = x
            }
        }
        // new list
        else {
            rates = list
            currencies = rates.map { it.currency }
            ratesVM = rates.map { getItemViewModel(it.currency, it.amount * baseRate.amount) }
            adapter.submitList(ratesVM)
        }

    }

    private fun getItemViewModel(currency: Currency, value: Double): ExchangeItemViewModel =
            ExchangeItemViewModel(stringResources, currency, value, this::updateAmount, this::updateCurrency)

    private fun updateAmount(currency: Currency, from: Double, to: Double) {
        if (exchangeCurrency != currency) return

        baseRate = baseRate.copy(amount = baseRate.amount * to / from)
        generateList(rates)
    }

    private fun updateCurrency(currency: Currency) {
        exchangeCurrency = currency
        ratesVM = ratesVM.sortedBy { if (it.currency == currency) "" else it.currency.name }
        adapter.submitList(ratesVM)
    }
}

