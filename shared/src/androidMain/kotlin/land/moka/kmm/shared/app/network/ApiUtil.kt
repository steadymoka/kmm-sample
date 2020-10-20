package land.moka.kmm.shared.app.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single

actual suspend fun <T> Flow<T>.singleExt(): T {
    return this.single()
}
