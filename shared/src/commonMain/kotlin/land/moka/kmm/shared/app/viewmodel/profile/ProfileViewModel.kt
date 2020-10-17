package land.moka.kmm.shared.app.viewmodel.profile

import com.apollographql.apollo.ApolloNetworkException
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import land.moka.kmm.shared.lib.livedata.MutableLiveData
import land.moka.kmm.shared.model.Organizer
import land.moka.kmm.shared.model.Pinned
import land.moka.kmm.shared.model.Profile
import land.moka.kmm.shared.model.Repository
import land.moka.kmm.shared.app.network.Api
import land.moka.kmm.shared.app.viewmodel.ViewModel

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

    private val _loading = BroadcastChannel<Boolean>(1)
    val loading get() = _loading.asFlow()

    private val _error = BroadcastChannel<Error>(1)
    val error get() = _error.asFlow()

    private val _selectedTab = BroadcastChannel<Tab>(1)
    val selectedTab get() = _selectedTab.asFlow()

    private val _profile = BroadcastChannel<Profile>(1)
    val profile get() = _profile.asFlow()

    private val _pinnedList = BroadcastChannel<List<Pinned>>(1)
    val pinnedList get() = _pinnedList.asFlow()

    private val _organizerList = BroadcastChannel<List<Organizer>>(1)
    val organizerList get() = _organizerList.asFlow()

    private val _myRepositoryList = BroadcastChannel<ArrayList<Repository>?>(1)
    val myRepositoryList get() = _myRepositoryList.asFlow()

    private var endCursorOfMyRepositories: String? = null

    suspend fun loadProfileData() {
        delay(1000) // to checking placeHolder
        val res = api.queryAboutMoka()
        val profileRes = res.search
            .edges
            ?.getOrNull(0)
            ?.node
            ?.asUser
        if (null != profileRes) {
            val profile = Profile(
                name = profileRes.name ?: "",
                avatarUrl = profileRes.avatarUrl.toString(),
                bio = profileRes.bio ?: "",
                status = Profile.Status(profileRes.status?.message ?: ""))
            this._profile.offer(profile)
        }

        val pinnedList = (res
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
        _pinnedList.offer(pinnedList)

        val organizerList = (res
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
        _organizerList.offer(organizerList)
    }

    suspend fun loadRepositories() {
        try {
            if (endCursorOfMyRepositories == "LAST") {
                return
            }

            _loading.offer(true)
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

            _loading.offer(false)
            _myRepositoryList.offer(repositories as ArrayList<Repository>)
            _error.offer(Error.NOPE)
        }
        catch (e: ApolloNetworkException) {
            _loading.offer(false)
            _error.offer(Error.CONNECTION)
        }
        catch (e: ApolloException) {
            _loading.offer(false)
            _error.offer(Error.SERVER)
        }
    }

    fun clearMyRepository() {
        _myRepositoryList.offer(null)
    }

    suspend fun reloadRepositories() {
        endCursorOfMyRepositories = null
        loadRepositories()
    }

    fun selectTab(tab: Tab) {
        _selectedTab.offer(tab)
    }

}
