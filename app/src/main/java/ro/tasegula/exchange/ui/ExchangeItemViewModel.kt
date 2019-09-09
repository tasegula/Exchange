package ro.tasegula.exchange.ui

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import ro.tasegula.exchange.BR
import ro.tasegula.exchange.core.StringResources
import ro.tasegula.exchange.core.arch.list.ItemViewModel
import ro.tasegula.exchange.data.Currency

class ExchangeItemViewModel(stringResources: StringResources,
                            val currency: Currency,
                            _amount: Double,
                            private val updateAmount: (currency: Currency, from: Double, to: Double) -> Unit,
                            private val updateCurrency: (Currency) -> Unit)
    : BaseObservable(), ItemViewModel {

    val icon: Int = currency.icon
    val name: String = currency.name
    val description: String = stringResources.getString(currency.description)

    var amount: Double = _amount
        set(value) {
            if (field == value) return
            field = value

            _amountText = field.formatted()
            notifyPropertyChanged(BR.amountText)
        }

    private var _amountText: String = amount.formatted()

    @get:Bindable
    var amountText: String
        get() = _amountText
        set(value) {
            if (_amountText == value) return

            updateAmount(currency, amount, value.toDoubleOrNull() ?: 0.0)
            _amountText = value
        }

    val onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus)
            updateCurrency(currency)
    }

    override fun isSameItemAs(other: ItemViewModel): Boolean =
            other is ExchangeItemViewModel && this.currency == other.currency

    override fun isSameContentAs(other: ItemViewModel): Boolean =
            other is ExchangeItemViewModel && this.amount == other.amount

    interface Commands {
        fun updateAmount(currency: Currency, from: Double, to: Double)
        fun setFocus(currency: Currency)
    }
}
