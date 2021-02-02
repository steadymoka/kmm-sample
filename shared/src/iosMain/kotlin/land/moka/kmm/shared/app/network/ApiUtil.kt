package land.moka.kmm.shared.app.network

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import land.moka.kmm.shared.lib.util.dispatcher
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@InternalCoroutinesApi
actual suspend fun <T> Flow<T>.singleExt(): T {
    return suspendCoroutine { continuation ->
        GlobalScope.launch(dispatcher()) {
            continuation.resume(this@singleExt.single())
        }
    }
}

@InternalCoroutinesApi
fun <T> Flow<T>.observe(action: (T) -> Unit) {
    GlobalScope.launch(dispatcher()) {
        this@observe.collect { action(it) }
    }
}
