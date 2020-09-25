package land.moka.kmm.shared.module.viewmodel.repository

import com.apollographql.apollo.ApolloHttpException
import com.apollographql.apollo.ApolloNetworkException
import land.moka.kmm.shared.lib.livedata.MutableLiveData
import land.moka.kmm.shared.model.Repository
import land.moka.kmm.shared.module.network.Api
import land.moka.kmm.shared.module.viewmodel.ViewModel

class RepositoryViewModel(private val api: Api) : ViewModel {

    enum class Error {
        CONNECTION, SERVER, NOPE
    }

    var loading = MutableLiveData(true)

    var error = MutableLiveData(Error.NOPE)

    var repository = MutableLiveData<Repository?>(null)

    //

    suspend fun loadRepository(name: String) {
        try {
            loading.value = true

            val res = api.queryRepository(name)
            val repo = (res
                .search
                .edges
                ?.getOrNull(0)
                ?.node
                ?.asUser)
                ?.repository
            repository.value = if (null == repo) {
                null
            }
            else {
                Repository(repo.name, repo.description ?: "")
            }

            error.value = Error.NOPE
        }
        catch (e: ApolloNetworkException) {
            error.value = Error.CONNECTION
        }
        catch (e: ApolloHttpException) {
            error.value = Error.SERVER
        }
        finally {
            loading.value = false
        }
    }

}
