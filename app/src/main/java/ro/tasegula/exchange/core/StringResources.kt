package ro.tasegula.exchange.core

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Injectable component that provides access to string resources.
 */
@Singleton
class StringResources
@Inject constructor(@Named("ApplicationContext") private val context: Context) {

    /**
     * @see Context.getString
     */
    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    /**
     * @see Context.getString
     */
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }

    fun getQuantityString(@PluralsRes resId: Int, amount: Long): String {
        return context.resources.getQuantityString(resId, amount.toInt(), amount.toInt())
    }

    fun getQuantityString(@PluralsRes resId: Int, amount: Long, vararg formatArgs: Any?): String {
        return context.resources.getQuantityString(resId, amount.toInt(), *formatArgs)
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }
}