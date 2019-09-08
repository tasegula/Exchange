package ro.tasegula.exchange

import ro.tasegula.exchange.dagger.DaggerTestAppComponent
import ro.tasegula.exchange.dagger.TestAppComponent
import ro.tasegula.exchange.dagger.TestAppModule

// used in TestRunner
class TestApplication : Application() {

    override fun initDagger() {
        val appComponent: TestAppComponent = DaggerTestAppComponent.builder()
            .testAppModule(TestAppModule(this))
            .build()

        appComponent.inject(this)
    }
}
