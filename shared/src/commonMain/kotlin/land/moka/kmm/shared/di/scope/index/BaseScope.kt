package land.moka.kmm.shared.di.scope.index

import land.moka.kmm.shared.di.AppContainer

interface BaseScope {

    fun create(container: AppContainer)

    fun destroy(container: AppContainer)

}
