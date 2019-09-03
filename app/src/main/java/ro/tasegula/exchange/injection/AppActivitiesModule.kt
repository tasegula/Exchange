package ro.tasegula.exchange.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ro.tasegula.exchange.MainActivity

@Module
internal abstract class AppActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity
}
