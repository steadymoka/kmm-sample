package land.moka.kmm.shared.module

import land.moka.kmm.shared.module.viewmodel.ViewModel
import land.moka.kmm.shared.module.viewmodel.profile.ProfileViewModel
import land.moka.kmm.shared.module.viewmodel.repository.RepositoryViewModel
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

object KodeinApp {

    private val di = DI {
        import(appModule())
    }

    private val directDI = di.direct

    private val viewModels = hashMapOf<String, ViewModel>()

    fun getProfileViewModel(): ProfileViewModel {
        val viewModel = viewModels[ProfileViewModel::class.simpleName.toString()] as? ProfileViewModel
        if (null != viewModel) {
            return viewModel
        }
        val profileViewModel = ProfileViewModel(directDI.instance())
        viewModels[ProfileViewModel::class.simpleName.toString()] = profileViewModel
        return profileViewModel
    }

    fun removeProfileViewModel() {
        viewModels.remove(ProfileViewModel::class.simpleName.toString())
    }

    fun getRepositoryViewModel(): RepositoryViewModel {
        val viewModel = viewModels[RepositoryViewModel::class.simpleName.toString()] as? RepositoryViewModel
        if (null != viewModel) {
            return viewModel
        }
        val repositoryViewModel = RepositoryViewModel(directDI.instance())
        viewModels[RepositoryViewModel::class.simpleName.toString()] = repositoryViewModel
        return repositoryViewModel
    }

    fun removeRepositoryViewModel() {
        viewModels.remove(RepositoryViewModel::class.simpleName.toString())
    }

}
