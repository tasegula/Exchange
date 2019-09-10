package ro.tasegula.exchange.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ro.tasegula.exchange.R
import ro.tasegula.exchange.dagger.AppMocks
import ro.tasegula.exchange.dagger.TestComponents
import ro.tasegula.exchange.data.Currency
import ro.tasegula.exchange.data.ExchangeBase
import ro.tasegula.exchange.data.ExchangeRate
import ro.tasegula.exchange.ui.ExchangeActivity
import ro.tasegula.exchange.ui.formatted
import ro.tasegula.exchange.utils.espresso.recyclerview.checkItemViewAtPosition
import ro.tasegula.exchange.utils.espresso.recyclerview.itemsCountAssertion
import ro.tasegula.exchange.utils.espresso.recyclerview.performOnItemViewAtPosition
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class ExchangeTest : BaseTestSuite() {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ExchangeActivity::class.java, false, false)

    @Before
    fun setup() {
        TestComponents.app.appPreferences().baseRate = ExchangeRate(Currency.EUR, 1f)
    }

    @After
    fun cleanup() {
        TestComponents.app.appPreferences().baseRate = ExchangeRate(Currency.EUR, 1f)
    }

    @Test
    fun checkAllCurrenciesAreAvailable() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        val rates = AppMocks.exchangeWebServiceMock.base.rates
        onView(withId(R.id.recycler_view)).check(itemsCountAssertion(rates.size))
        Timber.d("[TEST] Test ended")
    }

    @Test
    fun whenChangingFirstCurrencyValue_thenAllCurrenciesShouldChange() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        // Find the response according to the base in prefs
        // and make sure we have more than 1 exchange rate
        val rates: List<ExchangeRate> = responseExchangeBase().rates
        assert(rates.size > 1)

        val firstRate = rates[0]
        val secondRate = rates[1]

        val inputValue: Float = 10f
        val checkedValue: Float = secondRate.amount / firstRate.amount * inputValue

        // set the amount on first position
        setAmountOnPosition(0, inputValue)
        // verify that the second one changed
        checkAmountOnPosition(1, checkedValue.formatted())

        Timber.d("[TEST] Test ended")
    }

    @Test
    fun whenChangingAmountToZero_thenAllCurrenciesShouldBeZero() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        // set the amount on first position to 0
        setAmountOnPosition(0, 0f)
        // verify that the second one changed to 0
        checkAmountOnPosition(1, 0f.formatted())

        Timber.d("[TEST] Test ended")
    }

    @Test
    fun whenChangingSecondCurrencyValue_thenCurrencyShouldMoveToFirst() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        val rates: List<ExchangeRate> = responseExchangeBase().rates
        assert(rates.size > 1)

        // check amount on first 2 rows
        checkAmountOnPosition(0, rates[0].amount.formatted())
        checkAmountOnPosition(1, rates[1].amount.formatted())

        // tap on second row
        onView(withId(R.id.recycler_view))
            .performOnItemViewAtPosition(1, R.id.input, ViewActions.click())

        // reorder list
        val selectedRate = rates[1]
        val newRates =
            rates.sortedBy { if (it.currency == selectedRate.currency) "" else it.currency.name }
        val secondRow = newRates[1]

        // check amount on first 2 rows
        checkAmountOnPosition(0, selectedRate.amount.formatted())
        checkAmountOnPosition(1, secondRow.amount.formatted())

        Timber.d("[TEST] Test ended")
    }

    // region Helper
    private fun checkAmountOnPosition(position: Int, value: String) {
        onView(withId(R.id.recycler_view))
            .checkItemViewAtPosition(position, R.id.input,
                                     viewAssertion = matches(withText(value)))
    }

    private fun setAmountOnPosition(position: Int, value: Float) {
        onView(withId(R.id.recycler_view))
            .performOnItemViewAtPosition(position, R.id.input, replaceText(value.toString()))
    }

    private fun responseExchangeBase(): ExchangeBase {
        val exchangeRate = TestComponents.app.appPreferences().baseRate
        return AppMocks.exchangeWebServiceMock.baseFor(exchangeRate)
    }
    // endregion

    companion object {
        fun Double.formatted() = "%.2f".format(this)
    }
}
