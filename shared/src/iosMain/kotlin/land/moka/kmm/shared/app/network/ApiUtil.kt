package land.moka.kmm.shared.app.network

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@InternalCoroutinesApi
actual suspend fun <T> Flow<T>.singleExt(): T {
    return suspendCoroutine { continuation ->
        GlobalScope.launch(MainLoopDispatcher) {
            continuation.resume(this@singleExt.single())
        }
    }
}

@InternalCoroutinesApi
fun <T> Flow<T>.observe(action: (T) -> Unit) {
    GlobalScope.launch(MainLoopDispatcher) {
        this@observe.collect { action(it) }
    }
}
