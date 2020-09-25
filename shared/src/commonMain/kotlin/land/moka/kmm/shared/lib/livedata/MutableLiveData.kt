package land.moka.kmm.shared.lib.livedata

expect open class MutableLiveData<T>(initialValue: T) : LiveData<T> {
    fun postValue(value: T)

    override var value: T
}
