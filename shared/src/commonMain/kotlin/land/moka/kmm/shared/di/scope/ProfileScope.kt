package land.moka.kmm.shared.di.scope

import land.moka.kmm.shared.app.viewmodel.profile.ProfileViewModel
import land.moka.kmm.shared.di.AppContainer
import org.kodein.di.instance

object ProfileScope : Scope {

    class ProfileContainer(
        var viewModel: ProfileViewModel
    )

    override fun onCreate() {
        AppContainer.profileContainer = ProfileContainer(AppContainer.directDI.instance())
    }

    override fun onDestroy() {
        AppContainer.profileContainer = null
    }

}
