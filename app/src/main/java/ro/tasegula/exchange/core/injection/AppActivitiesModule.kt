package ro.tasegula.exchange.core.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ro.tasegula.exchange.ui.ExchangeActivity

@Module
internal abstract class AppActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun bindExchangeActivity(): ExchangeActivity
}
