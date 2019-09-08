package ro.tasegula.exchange

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.tmurakami.dexopener.DexOpener

// The default runner
@Suppress("unused")
class TestRunner : AndroidJUnitRunner() {

    @Throws(ClassNotFoundException::class,
            IllegalAccessException::class,
            InstantiationException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        DexOpener.install(this) // call first to "open" final classes
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}
