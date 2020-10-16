package land.moka.kmm.shared.di

import land.moka.kmm.shared.di.module.databaseModule
import land.moka.kmm.shared.di.module.networkModule
import land.moka.kmm.shared.di.module.viewModelModules
import land.moka.kmm.shared.di.scope.ProfileScope
import land.moka.kmm.shared.di.scope.RepositoryScope
import org.kodein.di.DI
import org.kodein.di.direct

object AppContainer {

    private val di = DI {
        import(networkModule)
        import(databaseModule)
        import(viewModelModules)
    }
    val directDI = di.direct

    var profileContainer: ProfileScope.ProfileContainer? = null
    var repositoryContainer: RepositoryScope.RepositoryContainer? = null

}
