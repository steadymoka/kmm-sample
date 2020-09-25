package land.moka.kmm.shared.lib.livedata


expect fun <T, K, R> combineWith(liveData1: LiveData<T>, liveData2: LiveData<K>, block: (T?, K?) -> R): LiveData<R>

