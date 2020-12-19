package land.moka.kmm.shared.app.network.caller

import kotlinx.coroutines.Job

interface Caller<T> {

    fun setErrorHandler(handler: Handler): Caller<T>

    fun setGlobalErrorHandler(handler: Handler): Caller<T>

    suspend fun get(): T?

    suspend fun call(callback: (T?) -> Unit)

}

interface Handler {

    fun invoke(exception: Exception)

}

interface Subscriber {

    fun subscribe(work: (Exception) -> Unit): Job

}
