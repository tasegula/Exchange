package ro.tasegula.exchange.utils

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson

object MockHelper {

    inline fun <reified T> parseFromAssets(name: String): T {
        val gson = Gson()
        val jsonStr = AssetsReader.loadAssetTextAsString(
            InstrumentationRegistry.getInstrumentation().context, name)
        return gson.fromJson(jsonStr, T::class.java)
    }
}
