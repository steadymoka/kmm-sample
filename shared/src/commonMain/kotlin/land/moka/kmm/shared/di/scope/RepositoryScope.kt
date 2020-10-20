package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.app.viewmodel.repository.RepositoryViewModel
import land.moka.kmm.shared.di.AppContainer
import org.kodein.di.instance

class RepositoryScope : Scope {

    class RepositoryContainer(
        var viewModel: RepositoryViewModel
    )

    override fun onCreate(container: AppContainer) {
        container.repositoryContainer = RepositoryContainer(container.directDI.instance())
    }

    override fun onDestroy(container: AppContainer) {
        container.repositoryContainer = null
    }

}
