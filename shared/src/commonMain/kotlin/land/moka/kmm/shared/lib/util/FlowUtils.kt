package land.moka.kmm.shared.lib.util

import com.apollographql.apollo.api.internal.json.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@InternalCoroutinesApi
fun <T> ConflatedBroadcastChannel<T>.wrap(): CFlow<T> = CFlow(asFlow())

@InternalCoroutinesApi
fun <T> Flow<T>.wrap(): CFlow<T> = CFlow(this)

@InternalCoroutinesApi
class CFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job( /* ConferenceService.coroutineContext[Job] */ )

        onEach {
            block(it)
        }.launchIn(CoroutineScope(dispatcher() + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}