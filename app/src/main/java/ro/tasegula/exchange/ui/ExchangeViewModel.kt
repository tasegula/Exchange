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
                    private val repository: ExchangeRepository)
    : ObservableViewModel(), ExchangeItemViewModel.Commands {

    /**
     * All available currencies.
     */
    private var currencies: List<Currency> = listOf()

    /**
     * All available exchange rates.
     * [currencies] is a list of this rates' currencies.
     */
    private var rates: List<ExchangeRate> = listOf()

    /**
     * List of [ExchangeItemViewModel] that are shown in the screen.
     */
    private var ratesVM: List<ExchangeItemViewModel> = listOf()

    /**
     * The base currency for all rates and the associated amount.
     */
    private val baseRate: ExchangeRate
        get() = repository.baseRate

    /**
     * The currency currently selected
     */
    private var exchangeCurrency: Currency = Currency.EUR


    val adapter: ExchangeAdapter = ExchangeAdapter()

    init {
        repository.ratesDb()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                generateList(it)
            }
            .neverDispose()
    }

    private fun generateList(list: List<ExchangeRate>) {
        // same list, just updateAmount values
        if (list.map { it.currency }.minus(currencies).isEmpty()) {
            val ratesMap: Map<Currency, Float> = list.map { it.currency to it.amount }.toMap()

            ratesVM.forEach { vm ->
                val amount = (ratesMap[vm.currency] ?: 0f) * baseRate.amount
                if (vm.currency == Currency.AUD) {
                    Timber.d(
                        "update from %s to %s (map: %s, base: %s)",
                        vm.amount, amount,
                        ratesMap[vm.currency], baseRate
                    )
                }
                vm.amount = amount
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

    private fun getItemViewModel(currency: Currency, value: Float): ExchangeItemViewModel =
        ExchangeItemViewModel(stringResources, currency, value, this)

    // region IVM Commands
    override fun updateAmount(currency: Currency, amount: Float) {
        if (exchangeCurrency != currency) return
        repository.baseRate = ExchangeRate(currency, amount)
    }

    override fun updateCurrency(currency: Currency, amount: Float) {
        if (exchangeCurrency == currency) return
        exchangeCurrency = currency
        repository.baseRate = ExchangeRate(currency, amount)

        ratesVM = ratesVM.sortedBy { if (it.currency == currency) "" else it.currency.name }
        adapter.submitList(ratesVM)
    }
    // endregion
}
