package land.moka.kmm.shared.lib.livedata


actual fun <T, K, R> combineWith(liveData1: LiveData<T>, liveData2: LiveData<K>, block: (T?, K?) -> R): LiveData<R> {
    return LiveData(block(liveData1.value, liveData2.value))
}
