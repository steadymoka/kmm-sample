package land.moka.kmm.shared.lib.livedata.util

import land.moka.kmm.shared.lib.livedata.MutableLiveData

fun <T> MutableLiveData<ArrayList<T>>.addValues(values: ArrayList<T>) {
    this.value.addAll(values)
    this.value = this.value
}
