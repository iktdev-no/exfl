package no.iktdev.exfl.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CoroutinesIO: Coroutines() {

    /**
     * Returns a new CoroutineScope with IO dispatcher and a Job, using the global CoroutinesExceptionHandler instance.
     * This scope is appropriate for IO-bound tasks, such as reading or writing to files, network communication or database access.
     *
     * @return a CoroutineScope instance with the IO dispatcher and a Job
     */
    val scope = CoroutineScope(Dispatchers.IO + Job() + handler)


    override fun <T> async(context: CoroutineContext, start: CoroutineStart, block: () -> T): Deferred<T> {
        return scope.async { block() }
    }

    override fun <T> launch(context: CoroutineContext, start: CoroutineStart, block: () -> T): Job {
        return scope.launch { block() }
    }
}