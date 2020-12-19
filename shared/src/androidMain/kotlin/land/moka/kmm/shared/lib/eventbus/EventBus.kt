package land.moka.kmm.shared.lib.eventbus

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

actual class EventBus<T> : CoroutineScope {

    actual override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    private val channel = BroadcastChannel<T>(1)

    actual fun send(event: T) {
        this.launch {
            channel.send(event)
        }
    }

    actual fun subscribe(subs: (event: T) -> Unit): Job {
        return this.launch {
            channel.asFlow().collect { item ->
                withContext(Dispatchers.Main) {
                    subs.invoke(item)
                }
            }
        }
    }

}
