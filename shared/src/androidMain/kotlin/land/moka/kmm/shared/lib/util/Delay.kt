package land.moka.kmm.shared.lib.util

import kotlinx.coroutines.delay

actual suspend fun delayExt(timeMillis: Long) {
    return delay(timeMillis)
}