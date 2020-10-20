package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.app.viewmodel.profile.ProfileViewModel
import land.moka.kmm.shared.di.AppContainer
import org.kodein.di.instance

class ProfileScope : Scope {

    class ProfileContainer(
        var viewModel: ProfileViewModel
    )

    override fun onCreate(container: AppContainer) {
        container.profileContainer = ProfileContainer(container.directDI.instance())
    }

    override fun onDestroy(container: AppContainer) {
        container.profileContainer = null
    }

}
