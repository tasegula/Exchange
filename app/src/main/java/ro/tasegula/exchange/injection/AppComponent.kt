package ro.tasegula.exchange.injection

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import ro.tasegula.exchange.Application
import javax.inject.Singleton

@Singleton
@Component(modules = [AppActivitiesModule::class, AndroidInjectionModule::class])
interface AppComponent {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}
