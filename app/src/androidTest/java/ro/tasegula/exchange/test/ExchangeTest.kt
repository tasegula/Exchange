package ro.tasegula.exchange.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ro.tasegula.exchange.ui.ExchangeActivity
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class ExchangeTest : BaseTestSuite() {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ExchangeActivity::class.java, false, false)

    @Test
    fun test() {
        Timber.d("[TEST] Test started")
        activityRule.launchActivity(null)


        Timber.d("[TEST] Test ended")
    }
}
