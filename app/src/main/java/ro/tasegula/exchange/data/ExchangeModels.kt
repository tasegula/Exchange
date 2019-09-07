package ro.tasegula.exchange.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import ro.tasegula.exchange.R

data class ExchangeRate(@SerializedName("base")
                        val base: String,
                        @SerializedName("date")
                        val date: String,
                        @SerializedName("rates")
                        val map: Map<String, Double>) {

    val rates: Map<Currency, Double>
        get() = map.mapKeys { Currency.valueOf(it.key) }
}

enum class Currency(@DrawableRes val icon: Int,
                    @StringRes val description: Int) {

    EUR(R.drawable.ic_launcher_foreground, R.string.currency_EUR_description),
    AUD(R.drawable.ic_launcher_foreground, R.string.currency_AUD_description),
    BGN(R.drawable.ic_launcher_foreground, R.string.currency_BGN_description),
    BRL(R.drawable.ic_launcher_foreground, R.string.currency_BRL_description),
    CAD(R.drawable.ic_launcher_foreground, R.string.currency_CAD_description),
    CHF(R.drawable.ic_launcher_foreground, R.string.currency_CHF_description),
    CNY(R.drawable.ic_launcher_foreground, R.string.currency_CNY_description),
    CZK(R.drawable.ic_launcher_foreground, R.string.currency_CZK_description),
    DKK(R.drawable.ic_launcher_foreground, R.string.currency_DKK_description),
    GBP(R.drawable.ic_launcher_foreground, R.string.currency_GBP_description),
    HKD(R.drawable.ic_launcher_foreground, R.string.currency_HKD_description),
    HRK(R.drawable.ic_launcher_foreground, R.string.currency_HRK_description),
    HUF(R.drawable.ic_launcher_foreground, R.string.currency_HUF_description),
    IDR(R.drawable.ic_launcher_foreground, R.string.currency_IDR_description),
    ILS(R.drawable.ic_launcher_foreground, R.string.currency_ILS_description),
    INR(R.drawable.ic_launcher_foreground, R.string.currency_INR_description),
    ISK(R.drawable.ic_launcher_foreground, R.string.currency_ISK_description),
    JPY(R.drawable.ic_launcher_foreground, R.string.currency_JPY_description),
    KRW(R.drawable.ic_launcher_foreground, R.string.currency_KRW_description),
    MXN(R.drawable.ic_launcher_foreground, R.string.currency_MXN_description),
    MYR(R.drawable.ic_launcher_foreground, R.string.currency_MYR_description),
    NOK(R.drawable.ic_launcher_foreground, R.string.currency_NOK_description),
    NZD(R.drawable.ic_launcher_foreground, R.string.currency_NZD_description),
    PHP(R.drawable.ic_launcher_foreground, R.string.currency_PHP_description),
    PLN(R.drawable.ic_launcher_foreground, R.string.currency_PLN_description),
    RON(R.drawable.ic_launcher_foreground, R.string.currency_RON_description),
    RUB(R.drawable.ic_launcher_foreground, R.string.currency_RUB_description),
    SEK(R.drawable.ic_launcher_foreground, R.string.currency_SEK_description),
    SGD(R.drawable.ic_launcher_foreground, R.string.currency_SGD_description),
    THB(R.drawable.ic_launcher_foreground, R.string.currency_THB_description),
    TRY(R.drawable.ic_launcher_foreground, R.string.currency_TRY_description),
    USD(R.drawable.ic_launcher_foreground, R.string.currency_USD_description),
    ZAR(R.drawable.ic_launcher_foreground, R.string.currency_ZAR_description),
}
