package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.app.viewmodel.repository.RepositoryViewModel
import land.moka.kmm.shared.di.AppContainer
import org.kodein.di.instance

object RepositoryScope : Scope {

    class RepositoryContainer(
        var viewModel: RepositoryViewModel
    )

    override fun onCreate() {
        AppContainer.repositoryContainer = RepositoryContainer(AppContainer.directDI.instance())
    }

    override fun onDestroy() {
        AppContainer.repositoryContainer = null
    }

}
