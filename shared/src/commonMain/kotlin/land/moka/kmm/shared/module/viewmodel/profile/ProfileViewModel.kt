package land.moka.kmm.shared.module.viewmodel.profile

import com.apollographql.apollo.ApolloNetworkException
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.delay
import land.moka.kmm.shared.lib.livedata.MutableLiveData
import land.moka.kmm.shared.lib.livedata.util.addValues
import land.moka.kmm.shared.model.Organizer
import land.moka.kmm.shared.model.Pinned
import land.moka.kmm.shared.model.Profile
import land.moka.kmm.shared.model.Repository
import land.moka.kmm.shared.module.network.Api
import land.moka.kmm.shared.module.viewmodel.ViewModel


class ProfileViewModel(private val api: Api) : ViewModel {

    enum class Error {
        CONNECTION, SERVER, NOPE
    }

    enum class Tab {
        Overview, Repositories;

        companion object {
            fun get(index: Int): Tab = values().filter { it.ordinal == index }[0]
        }
    }

    var loading = MutableLiveData(true)

    var error = MutableLiveData(Error.NOPE)

    val selectedTab = MutableLiveData(Tab.Overview)

    val profile = MutableLiveData(Profile.init())

    var pinnedList = MutableLiveData<List<Pinned>>(listOf())

    var organizerList = MutableLiveData<List<Organizer>>(listOf())

    var myRepositoryList = MutableLiveData<ArrayList<Repository>>(arrayListOf())

    private var endCursorOfMyRepositories: String? = null

    suspend fun loadProfileData() {
        delay(1000) // to checking placeHolder
        val res = api.queryAboutMoka()
        val profile = res.search
            .edges
            ?.getOrNull(0)
            ?.node
            ?.asUser
        if (null != profile) {
            this.profile.value = Profile(
                name = profile.name ?: "",
                avatarUrl = profile.avatarUrl.toString(),
                bio = profile.bio ?: "",
                status = Profile.Status(profile.status?.message ?: ""))
        }

        pinnedList.value = (res
            .search
            .edges
            ?.getOrNull(0)
            ?.node
            ?.asUser)
            ?.pinnedItems
            ?.edges?.mapNotNull {
                val repo = it?.node?.asRepository
                if (null != repo) {
                    Pinned(repo.id, repo.name, repo.description ?: "")
                } else {
                    null
                }
            } ?: listOf()

        organizerList.value = (res
            .search
            .edges
            ?.getOrNull(0)
            ?.node
            ?.asUser)
            ?.organizations
            ?.nodes
            ?.mapNotNull {
                if (null != it) {
                    Organizer(it.name ?: "", it.avatarUrl.toString(), it.description ?: "")
                } else {
                    null
                }
            } ?: listOf()
    }

    suspend fun loadRepositories() {
        try {
            if (endCursorOfMyRepositories == "LAST") {
                return
            }

            loading.value = true
            val res = api.queryMyRepositories(endCursorOfMyRepositories)
            val repositories = (res
                .search
                .edges
                ?.getOrNull(0)
                ?.node
                ?.asUser)
                ?.repositories
                ?.apply {
                    endCursorOfMyRepositories = pageInfo.endCursor ?: "LAST"
                }
                ?.edges
                ?.map {
                    val repo = it?.node
                    Repository(repo?.name ?: "", repo?.description ?: "")
                }

            loading.value = false
            myRepositoryList.addValues(repositories as ArrayList<Repository>)
            error.value = Error.NOPE
        }
        catch (e: ApolloNetworkException) {
            loading.value = false
            error.value = Error.CONNECTION
        }
        catch (e: ApolloException) {
            loading.value = false
            error.value = Error.SERVER
        }
    }

    suspend fun reloadRepositories() {
        endCursorOfMyRepositories = null
        loadRepositories()
    }

}
