package land.moka.kmm.shared.lib.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@InternalCoroutinesApi
actual suspend fun delayExt(timeMillis: Long) {
    return suspendCoroutine { continuation ->
        GlobalScope.launch(dispatcher()) {
            delay(timeMillis)
            continuation.resume(Unit)
        }
    }
}
