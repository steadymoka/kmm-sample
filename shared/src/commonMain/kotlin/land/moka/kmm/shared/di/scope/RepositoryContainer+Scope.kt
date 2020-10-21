package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.app.viewmodel.repository.RepositoryViewModel
import land.moka.kmm.shared.di.AppContainer
import land.moka.kmm.shared.di.scope.index.Container
import land.moka.kmm.shared.di.scope.index.Scope
import org.kodein.di.instance

class RepositoryContainer(
    var viewModel: RepositoryViewModel
) : Container {

    companion object : Scope {

        override fun onCreate(container: AppContainer) {
            container.childContainers[RepositoryContainer::class.simpleName ?: "RepositoryContainer"] = RepositoryContainer(container.directDI.instance())
        }

        override fun onDestroy(container: AppContainer) {
            container.childContainers.remove(RepositoryContainer::class.simpleName ?: "RepositoryContainer")
        }

    }

}
