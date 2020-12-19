package land.moka.kmm.shared.lib.eventbus

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

expect class EventBus<T> : CoroutineScope {

    override val coroutineContext: CoroutineContext

    constructor()

    @InternalCoroutinesApi
    fun send(event: T)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun subscribe(subs: (event: T) -> Unit): Job
}
