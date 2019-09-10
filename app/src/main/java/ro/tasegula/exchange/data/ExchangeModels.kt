package ro.tasegula.exchange.data

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ro.tasegula.exchange.R
import ro.tasegula.exchange.core.db.EnumTypeConverter

data class ExchangeBase(@field:SerializedName("base")
                        val base: Currency,
                        @field:SerializedName("date")
                        val date: String,
                        @field:SerializedName("rates")
                        val map: Map<Currency, Float>) {

    private inline val baseExchangeRate: ExchangeRate
        get() = ExchangeRate(base, 1f)

    private inline val mapExchangeRates: List<ExchangeRate>
        get() = map.map { ExchangeRate(it.key, it.value) }.sortedBy { it.currency }

    val rates: List<ExchangeRate>
        get() = listOf(baseExchangeRate).plus(mapExchangeRates)

}

@Keep
@Entity
@TypeConverters(Currency.Converter::class)
data class ExchangeRate(@PrimaryKey
                        val currency: Currency,
                        val amount: Float)

enum class Currency(@DrawableRes val icon: Int,
                    @StringRes val description: Int) {

    @SerializedName("AUD")
    AUD(R.drawable.ic_currency_aud, R.string.currency_AUD_description),
    @SerializedName("BGN")
    BGN(R.drawable.ic_currency_bgn, R.string.currency_BGN_description),
    @SerializedName("BRL")
    BRL(R.drawable.ic_currency_brl, R.string.currency_BRL_description),
    @SerializedName("CAD")
    CAD(R.drawable.ic_currency_cad, R.string.currency_CAD_description),
    @SerializedName("CHF")
    CHF(R.drawable.ic_currency_chf, R.string.currency_CHF_description),
    @SerializedName("CNY")
    CNY(R.drawable.ic_currency_cny, R.string.currency_CNY_description),
    @SerializedName("CZK")
    CZK(R.drawable.ic_currency_czk, R.string.currency_CZK_description),
    @SerializedName("DKK")
    DKK(R.drawable.ic_currency_dkk, R.string.currency_DKK_description),
    @SerializedName("EUR")
    EUR(R.drawable.ic_currency_eur, R.string.currency_EUR_description),
    @SerializedName("GBP")
    GBP(R.drawable.ic_currency_gbp, R.string.currency_GBP_description),
    @SerializedName("HKD")
    HKD(R.drawable.ic_currency_hkd, R.string.currency_HKD_description),
    @SerializedName("HRK")
    HRK(R.drawable.ic_currency_hrk, R.string.currency_HRK_description),
    @SerializedName("HUF")
    HUF(R.drawable.ic_currency_huf, R.string.currency_HUF_description),
    @SerializedName("IDR")
    IDR(R.drawable.ic_currency_idr, R.string.currency_IDR_description),
    @SerializedName("ILS")
    ILS(R.drawable.ic_currency_ils, R.string.currency_ILS_description),
    @SerializedName("INR")
    INR(R.drawable.ic_currency_inr, R.string.currency_INR_description),
    @SerializedName("ISK")
    ISK(R.drawable.ic_currency_isk, R.string.currency_ISK_description),
    @SerializedName("JPY")
    JPY(R.drawable.ic_currency_jpy, R.string.currency_JPY_description),
    @SerializedName("KRW")
    KRW(R.drawable.ic_currency_krw, R.string.currency_KRW_description),
    @SerializedName("MXN")
    MXN(R.drawable.ic_currency_mxn, R.string.currency_MXN_description),
    @SerializedName("MYR")
    MYR(R.drawable.ic_currency_myr, R.string.currency_MYR_description),
    @SerializedName("NOK")
    NOK(R.drawable.ic_currency_nok, R.string.currency_NOK_description),
    @SerializedName("NZD")
    NZD(R.drawable.ic_currency_nzd, R.string.currency_NZD_description),
    @SerializedName("PHP")
    PHP(R.drawable.ic_currency_php, R.string.currency_PHP_description),
    @SerializedName("PLN")
    PLN(R.drawable.ic_currency_pln, R.string.currency_PLN_description),
    @SerializedName("RON")
    RON(R.drawable.ic_currency_ron, R.string.currency_RON_description),
    @SerializedName("RUB")
    RUB(R.drawable.ic_currency_rub, R.string.currency_RUB_description),
    @SerializedName("SEK")
    SEK(R.drawable.ic_currency_sek, R.string.currency_SEK_description),
    @SerializedName("SGD")
    SGD(R.drawable.ic_currency_sbg, R.string.currency_SGD_description),
    @SerializedName("THB")
    THB(R.drawable.ic_currency_tbh, R.string.currency_THB_description),
    @SerializedName("TRY")
    TRY(R.drawable.ic_currency_try, R.string.currency_TRY_description),
    @SerializedName("USD")
    USD(R.drawable.ic_currency_usd, R.string.currency_USD_description),
    @SerializedName("ZAR")
    ZAR(R.drawable.ic_currency_zar, R.string.currency_ZAR_description);

    class Converter : EnumTypeConverter<Currency>(Currency::class.java)

    companion object {
        fun from(name: String, default: Currency = EUR): Currency {
            return try {
                valueOf(name)
            } catch (e: Exception) {
                default
            }
        }
    }
}
