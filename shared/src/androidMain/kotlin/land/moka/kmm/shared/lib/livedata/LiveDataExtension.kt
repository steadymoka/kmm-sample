package land.moka.kmm.shared.lib.livedata

import androidx.lifecycle.MediatorLiveData

actual fun <T, K, R> combineWith(liveData1: LiveData<T>, liveData2: LiveData<K>, block: (T?, K?) -> R): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(liveData1.ld()) {
        result.value = block.invoke(liveData1.value, liveData2.value)
    }
    result.addSource(liveData2.ld()) {
        result.value = block.invoke(liveData1.value, liveData2.value)
    }
    return LiveData(result)
}
