package land.moka.kmm.shared.lib.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi

@Suppress("NO_ACTUAL_FOR_EXPECT")
@InternalCoroutinesApi
internal expect fun dispatcher(): CoroutineDispatcher
