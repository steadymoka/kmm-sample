package land.moka.kmm.shared.lib.livedata

import androidx.lifecycle.MutableLiveData

actual open class MutableLiveData<T> : LiveData<T> {

    actual constructor(initialValue: T) : super(MutableLiveData<T>().apply { value = initialValue })

    constructor(mutableLiveData: MutableLiveData<T>) : super(mutableLiveData)

    @Suppress("UNCHECKED_CAST")
    actual override var value: T
        get() = archLiveData.value as T
        set(newValue) {
            (archLiveData as MutableLiveData<T>).value = newValue
        }

    actual fun postValue(value: T) {
        (archLiveData as MutableLiveData<T>).postValue(value)
    }

    override fun ld(): MutableLiveData<T> = archLiveData as MutableLiveData<T>

}
