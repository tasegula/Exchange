package ro.tasegula.exchange

import ro.tasegula.exchange.dagger.DaggerTestAppComponent
import ro.tasegula.exchange.dagger.TestAppComponent
import ro.tasegula.exchange.dagger.TestAppModule
import ro.tasegula.exchange.dagger.TestComponents

// used in TestRunner
class TestApplication : Application() {

    override fun initDagger() {
        val appComponent: TestAppComponent = DaggerTestAppComponent.builder()
            .testAppModule(TestAppModule(this))
            .build()

        TestComponents.initialize(appComponent)
        appComponent.inject(this)
    }
}
