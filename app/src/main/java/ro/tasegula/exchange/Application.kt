package ro.tasegula.exchange

import android.app.Activity
import androidx.annotation.VisibleForTesting
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ro.tasegula.exchange.core.injection.AppComponent
import ro.tasegula.exchange.core.injection.AppModule
import ro.tasegula.exchange.core.injection.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
open class Application : android.app.Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return Thread.currentThread().name + '/'.toString() +
                            super.createStackElementTag(element)
                }
            })
        initDagger()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected open fun initDagger() {
        val appComponent: AppComponent =
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
}
