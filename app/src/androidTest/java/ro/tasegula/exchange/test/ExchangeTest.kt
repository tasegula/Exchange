package ro.tasegula.exchange.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ro.tasegula.exchange.R
import ro.tasegula.exchange.dagger.AppMocks
import ro.tasegula.exchange.ui.ExchangeActivity
import ro.tasegula.exchange.utils.espresso.recyclerview.checkItemViewAtPosition
import ro.tasegula.exchange.utils.espresso.recyclerview.itemsCountAssertion
import ro.tasegula.exchange.utils.espresso.recyclerview.performOnItemViewAtPosition
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class ExchangeTest : BaseTestSuite() {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ExchangeActivity::class.java, false, false)

    @Test
    fun checkAllCurrenciesAreAvailable() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        val rates = AppMocks.exchangeWebServiceMock.rates
        onView(withId(R.id.recycler_view)).check(itemsCountAssertion(rates.size))
        Timber.d("[TEST] Test ended")
    }

    @Test
    fun whenChangingFirstCurrencyValue_thenAllCurrenciesShouldChange() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        val rates = AppMocks.exchangeWebServiceMock.rates
        assert(rates.size > 1)

        val firstRate = rates[0]
        val secondRate = rates[1]

        val inputValue: Double = 10.0
        val checkedValue: Double = secondRate.amount * firstRate.amount * inputValue

        setAmountOnPosition(0, inputValue)
        checkAmountOnPosition(1, checkedValue)

        Timber.d("[TEST] Test ended")
    }

    @Test
    fun whenChangingSecondCurrencyValue_thenCurrencyShouldMoveToFirst() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)

        val rates = AppMocks.exchangeWebServiceMock.rates
        assert(rates.size > 1)

        // check amount on first 2 rows
        checkAmountOnPosition(0, rates[0].amount)
        checkAmountOnPosition(1, rates[1].amount)

        // tap on second row
        onView(withId(R.id.recycler_view))
                .performOnItemViewAtPosition(1, R.id.input, ViewActions.click())

        // reorder list
        val selectedRate = rates[1]
        val newRates = rates.sortedBy { if (it.currency == selectedRate.currency) "" else it.currency.name }
        val secondRow = newRates[1]

        // check amount on first 2 rows
        checkAmountOnPosition(0, selectedRate.amount)
        checkAmountOnPosition(1, secondRow.amount)

        Timber.d("[TEST] Test ended")
    }

    private fun checkAmountOnPosition(position: Int, value: Double) {
        onView(withId(R.id.recycler_view))
                .checkItemViewAtPosition(position, R.id.input,
                        viewAssertion = matches(withText(value.formatted())))
    }

    private fun setAmountOnPosition(position: Int, value: Double) {
        onView(withId(R.id.recycler_view))
                .performOnItemViewAtPosition(position, R.id.input, replaceText(value.toString()))
    }

    companion object {
        fun Double.formatted() = "%.2f".format(this)
    }
}
