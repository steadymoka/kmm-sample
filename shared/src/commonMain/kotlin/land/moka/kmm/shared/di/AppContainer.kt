package land.moka.kmm.shared.di

import land.moka.kmm.shared.di.module.databaseModule
import land.moka.kmm.shared.di.module.networkModule
import land.moka.kmm.shared.di.module.viewModelModules
import land.moka.kmm.shared.di.scope.index.Container
import org.kodein.di.DI
import org.kodein.di.direct

class AppContainer private constructor() {

    private val di = DI {
        import(networkModule)
        import(databaseModule)
        import(viewModelModules)
    }
    val directDI = di.direct

    var childContainers = hashMapOf<String, Container>()

    init {
        // setup container
    }

    inline fun <reified T : Container> getContainer(): T { // for android build
        return childContainers[T::class.simpleName] as? T ?: throw Exception("ChildContainer::onCreate(container: Container) must be called 👻")
    }

    fun <T : Container> getContainer(name: String): T { // for iOS build
        return childContainers[name] as? T ?: throw Exception("ChildContainer::onCreate(container: Container) must be called 👻")
    }

    companion object {
        fun start(): AppContainer {
            return AppContainer()
        }
    }

}
