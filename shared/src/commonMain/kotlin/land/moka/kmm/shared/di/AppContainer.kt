package land.moka.kmm.shared.di

import land.moka.kmm.shared.di.module.appModule
import land.moka.kmm.shared.di.module.networkModule
import land.moka.kmm.shared.di.module.viewModelModules
import land.moka.kmm.shared.di.scope.index.Container
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

class AppContainer private constructor() {

    private val di = DI {
        import(networkModule)
        import(appModule)
        import(viewModelModules)
    }
    val directDI = di.direct

    var childContainers = hashMapOf<String, Container>()

    init {
        // setup container
    }

    /**
     * for android build
     */

    inline fun <reified T : Any> get(): T {
        return directDI.instance()
    }

    inline fun <reified T : Container> getContainer(): T { // for android build
        return childContainers[T::class.simpleName] as? T ?: throw Exception("ChildContainer::onCreate(container: Container) must be called 👻")
    }

    /**
     * for iOS build
     */

    fun <T : Container> getContainer(name: String): T { // for iOS build
        return childContainers[name] as? T ?: throw Exception("ChildContainer::onCreate(container: Container) must be called 👻")
    }

    companion object {
        fun start(): AppContainer {
            return AppContainer()
        }
    }

}
