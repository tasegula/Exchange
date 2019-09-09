package ro.tasegula.exchange.core.injection

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ro.tasegula.exchange.Application
import ro.tasegula.exchange.core.db.ExchangeDatabase
import ro.tasegula.exchange.data.ExchangeDao
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(application: Application) : BaseAppModule(application)

@Module
open class BaseAppModule(private val application: Application) {

    @Provides
    @Named("ApplicationContext")
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(@Named("ApplicationContext") context: Context): ExchangeDatabase {
        return ExchangeDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@Named("ApplicationContext") context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_PREFERENCES_FILENAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideExchangeDao(database: ExchangeDatabase): ExchangeDao = database.exchangeDao

    companion object {
        const val APP_PREFERENCES_FILENAME = "ro.tasegula.exchange.preferences.app"
    }
}
