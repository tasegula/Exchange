package ro.tasegula.exchange.injection

import dagger.Component
import dagger.android.AndroidInjectionModule
import ro.tasegula.exchange.Application
import javax.inject.Singleton

@Singleton
@Component(modules = [AppActivitiesModule::class, AndroidInjectionModule::class, AppModule::class])
interface AppComponent {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}
