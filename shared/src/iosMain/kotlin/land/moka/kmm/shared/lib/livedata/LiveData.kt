package land.moka.kmm.shared.lib.livedata

actual open class LiveData<T>(initialValue: T) {
    private var storedValue: T = initialValue
    private val observers = mutableListOf<(T) -> Unit>()

    actual open val value: T
        get() = storedValue

    actual fun addObserver(observer: (T) -> Unit) {
        observer(value)
        observers.add(observer)
    }

    actual fun removeObserver(observer: (T) -> Unit) {
        observers.remove(observer)
    }

    protected fun changeValue(value: T) {
        storedValue = value

        observers.forEach { it(value) }
    }
}
