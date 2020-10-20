package land.moka.kmm.shared.app.network

import kotlinx.coroutines.flow.Flow

expect suspend fun <T> Flow<T>.singleExt(): T
