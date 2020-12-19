package land.moka.kmm.shared.lib.eventbus

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import land.moka.kmm.shared.lib.util.dispatcher
import kotlin.coroutines.CoroutineContext

actual class EventBus<T> : CoroutineScope {

    actual override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val channel = BroadcastChannel<T>(1)

    @InternalCoroutinesApi
    actual fun send(event: T) {
        this.launch(dispatcher()) {
            channel.send(event)
        }
    }

    @InternalCoroutinesApi
    actual fun subscribe(subs: (event: T) -> Unit): Job {
        return this.launch(dispatcher()) {
            channel.asFlow().collect { item ->
                withContext(dispatcher()) {
                    subs.invoke(item)
                }
            }
        }
    }

}
