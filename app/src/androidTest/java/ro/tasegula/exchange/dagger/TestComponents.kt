package ro.tasegula.exchange.dagger

import ro.tasegula.exchange.test.BuildConfig

object TestComponents {

    @JvmStatic
    private lateinit var sComponent: TestAppComponent

    @JvmStatic
    fun initialize(component: TestAppComponent) {
        // Initialize the AppComponent
        check(!::sComponent.isInitialized) { "Application Component already initialized" }
        sComponent = component
    }

    @JvmStatic
    val app: TestAppComponent
        get() {
            if (BuildConfig.DEBUG)
                check(::sComponent.isInitialized) { "Application component not initialized." }
            return sComponent
        }
}
