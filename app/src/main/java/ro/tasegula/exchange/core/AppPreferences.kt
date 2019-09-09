package ro.tasegula.exchange.core

import android.content.SharedPreferences
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeRate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences
@Inject constructor(private val preferences: SharedPreferences) {

    var baseRate: ExchangeRate
        get() = ExchangeRate(baseCurrency, baseAmount)
        set(value) {
            baseCurrency = value.currency
            baseAmount = value.amount
        }

    private var baseCurrency: Currency
        get() = Currency.from(preferences.getString(BASE_CURRENCY, Currency.EUR.name)!!)
        set(value) {
            preferences.edit().putString(BASE_CURRENCY, value.name).apply()
        }

    private var baseAmount: Float
        get() = preferences.getFloat(BASE_AMOUNT, 0f)
        set(value) {
            preferences.edit().putFloat(BASE_CURRENCY, value).apply()
        }

    companion object {
        const val PREFIX = "ro.tasegula.exchange."

        const val BASE_CURRENCY = PREFIX + "base.currency"
        const val BASE_AMOUNT = PREFIX + "base.amount"
    }
}
