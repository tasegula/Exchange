package ro.tasegula.exchange.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import ro.tasegula.exchange.Application
import javax.inject.Named

@Module
class AppModule(private val application: Application) {

    @Provides
    @Named("ApplicationContext")
    fun provideContext(): Context {
        return application.applicationContext
    }
}