@file:Suppress("NOTHING_TO_INLINE")

package ro.tasegula.exchange.utils

import org.mockito.AdditionalMatchers
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.isNull
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

inline fun <T> on(callMethod: T): OngoingStubbing<T> = Mockito.`when`(callMethod)
inline fun <T> at(callMethod: T): OngoingStubbing<T> = Mockito.`when`(callMethod)
inline fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!

inline fun <T> OngoingStubbing<T>.doReturn(builder: () -> T): OngoingStubbing<T> =
    this.thenReturn(builder.invoke())

inline fun <reified T> anyk(): T = ArgumentMatchers.any<T>(T::class.java)

inline fun <reified T> anyOrNull(): T = AdditionalMatchers.or(isNull(), anyk<T>())
