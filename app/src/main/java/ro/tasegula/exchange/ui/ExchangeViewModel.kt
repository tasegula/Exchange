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

    init {
        repository.getRates(Currency.EUR)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            makeList(it.rates)
                        },
                        {
                        })
    }

    val adapter: ExchangeAdapter = ExchangeAdapter()

    private fun makeList(rates: Map<String, Double>) {
        val list = rates.map { RateItemViewModel(stringResources, Currency.valueOf(it.key), it.value) }
        adapter.submitList(list)
    }
}

class RateItemViewModel(stringResources: StringResources,
                        model: Currency,
                        rate: Double) : ItemViewModel {

    val icon: Int = model.icon
    val currency: String = model.name
    val description: String = stringResources.getString(model.description)
    val amount: String = "%.2f".format(rate)

    override fun isSameItemAs(other: ItemViewModel): Boolean = false

    override fun isSameContentAs(other: ItemViewModel): Boolean = false
}
