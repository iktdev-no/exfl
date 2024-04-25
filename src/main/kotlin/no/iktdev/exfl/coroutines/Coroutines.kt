package no.iktdev.exfl.coroutines

import kotlinx.coroutines.*
import no.iktdev.exfl.observable.Observables
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class Coroutines {
    private val _exception = Observables.ObservableValue<Throwable>()
    val handler = CoroutinesExceptionHandler(_exception)


    fun addListener(listener: Observables.ObservableValue.ValueListener<Throwable>): Coroutines {
        _exception.addListener(listener)
        return this
    }

    abstract fun <T> async(context: CoroutineContext = EmptyCoroutineContext,
                  start: CoroutineStart = CoroutineStart.DEFAULT,
                  block: suspend CoroutineScope.() -> T
    ): Deferred<T>

    abstract fun <T> launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T): Job

    /**
     * A global exception handler that catches unhandled exceptions thrown in Coroutines and stores them in a LiveData instance.
     * The key of this handler is a CoroutineExceptionHandler.Key instance.
     */
    class CoroutinesExceptionHandler(private val exceptionObserver: Observables.ObservableValue<Throwable>) : CoroutineExceptionHandler {
        /**
         * The key of this CoroutineExceptionHandler instance.
         */
        override val key = CoroutineExceptionHandler.Key

        /**
         * Handles an uncaught exception thrown by a Coroutine by storing it in a LiveData instance.
         *
         * @param context the CoroutineContext of the failed Coroutine
         * @param exception the Throwable instance representing the exception that was thrown
         */
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            var cause: Throwable? = null
            var current = exception
            while (current.cause != null) {
                cause = current.cause
                current = current.cause!!
            }

            if (cause != null) {
                exceptionObserver.next(cause)
            } else {
                exceptionObserver.next(exception)
            }
        }
    }
}