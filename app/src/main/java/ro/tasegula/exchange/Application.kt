package ro.tasegula.exchange

import android.app.Activity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ro.tasegula.exchange.injection.AppComponent
import ro.tasegula.exchange.injection.DaggerAppComponent
import javax.inject.Inject

class Application : android.app.Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        val appComponent: AppComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
    }
}
