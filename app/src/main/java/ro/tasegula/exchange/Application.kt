package ro.tasegula.exchange

import android.app.Activity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ro.tasegula.exchange.core.injection.AppComponent
import ro.tasegula.exchange.core.injection.AppModule
import ro.tasegula.exchange.core.injection.DaggerAppComponent
import javax.inject.Inject

class Application : android.app.Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        val appComponent: AppComponent =
                DaggerAppComponent.builder()
                        .appModule(AppModule(this))
                        .build()
        appComponent.inject(this)
    }
}
