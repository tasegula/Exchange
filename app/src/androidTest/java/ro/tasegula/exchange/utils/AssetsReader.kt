package ro.tasegula.exchange.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException

object AssetsReader {

    fun loadAssetTextAsString(context: Context, name: String): String? {
        var reader: BufferedReader? = null
        try {
            reader = context.assets.open(name).bufferedReader()
            return reader.readText()
        } catch (e: IOException) {
            println("Error opening asset '$name' : ${e.message}")
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                println("Error closing asset '$name' ${e.message}")
            }
        }
        return null
    }
}
