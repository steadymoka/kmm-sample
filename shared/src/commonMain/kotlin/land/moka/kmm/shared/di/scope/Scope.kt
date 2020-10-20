package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.di.AppContainer

interface Scope {

    fun onCreate(container: AppContainer)

    fun onDestroy(container: AppContainer)

}
