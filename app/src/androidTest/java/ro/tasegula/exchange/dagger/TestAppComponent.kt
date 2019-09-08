package ro.tasegula.exchange.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import ro.tasegula.exchange.Application
import ro.tasegula.exchange.core.injection.AppActivitiesModule
import ro.tasegula.exchange.core.injection.AppComponent
import ro.tasegula.exchange.core.injection.BaseAppModule
import ro.tasegula.exchange.data.ExchangeWebService
import ro.tasegula.exchange.mocks.ExchangeWebServiceMock
import javax.inject.Singleton

@Singleton
@Component(modules = [AppActivitiesModule::class, AndroidInjectionModule::class, TestAppModule::class])
interface TestAppComponent : AppComponent {

}

@Module
class TestAppModule(application: Application) : BaseAppModule(application) {

    @Provides
    fun exchangeWebService(): ExchangeWebService = AppMocks.exchangeWebService
}

object AppMocks {
    val exchangeWebService: ExchangeWebService by lazy { exchangeWebServiceMock.mocked() }

    val exchangeWebServiceMock: ExchangeWebServiceMock = ExchangeWebServiceMock()
}