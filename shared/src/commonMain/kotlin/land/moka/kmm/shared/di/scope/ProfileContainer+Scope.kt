package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.app.viewmodel.profile.ProfileViewModel
import land.moka.kmm.shared.di.AppContainer
import land.moka.kmm.shared.di.scope.index.Container
import land.moka.kmm.shared.di.scope.index.Scope
import org.kodein.di.instance

class ProfileContainer(
    var viewModel: ProfileViewModel
) : Container {

    companion object : Scope {

        override fun onCreate(container: AppContainer) {
            container.childContainers[ProfileContainer::class.simpleName ?: "ProfileContainer"] = ProfileContainer(container.directDI.instance())
        }

        override fun onDestroy(container: AppContainer) {
            container.childContainers.remove(ProfileContainer::class.simpleName ?: "ProfileContainer")
        }

    }

}
