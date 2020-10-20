package land.moka.kmm.shared.di.module

import land.moka.kmm.shared.app.viewmodel.profile.ProfileViewModel
import land.moka.kmm.shared.app.viewmodel.repository.RepositoryViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

val viewModelModules = DI.Module("viewModels") {
    bind<ProfileViewModel>() with factory { ProfileViewModel(instance()) }
    bind<RepositoryViewModel>() with factory { RepositoryViewModel(instance()) }
}
