package ro.tasegula.exchange.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

inline fun targetContext(): Context =
    InstrumentationRegistry.getInstrumentation().targetContext
