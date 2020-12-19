package land.moka.kmm.shared.app.network.error

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import land.moka.kmm.shared.lib.eventbus.EventBus

class GlobalErrorHandler : AppErrorHandler() {

    private var errorEventBus = EventBus<TokenException>()

    @InternalCoroutinesApi
    override fun invoke(exception: Exception) {
        errorEventBus.send(exception as TokenException)
    }

    @InternalCoroutinesApi
    fun send(errorCode: String, message: String) {
        val exception = parseException(errorCode, message)
        this.invoke(exception)
    }

    @InternalCoroutinesApi
    override fun subscribe(work: (Exception) -> Unit): Job {
        return errorEventBus.subscribe {
            work(it)
        }
    }

}
