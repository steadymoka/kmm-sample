package land.moka.kmm.shared.app.viewmodel.repository

import com.apollographql.apollo.ApolloHttpException
import com.apollographql.apollo.ApolloNetworkException
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import land.moka.kmm.shared.model.Repository
import land.moka.kmm.shared.app.network.Api
import land.moka.kmm.shared.app.viewmodel.ViewModel

class RepositoryViewModel(private val api: Api) : ViewModel {

    enum class Error {
        CONNECTION, SERVER, NOPE
    }

    private val _loading = BroadcastChannel<Boolean>(1)
    val loading get() = _loading.asFlow()

    private val _error = BroadcastChannel<Error>(1)
    val error get() = _error.asFlow()

    private val _repository = BroadcastChannel<Repository?>(1)
    val repository get() = _repository.asFlow()

    @Throws(Exception::class)
    suspend fun loadRepository(name: String) {
        try {
            _loading.offer(true)

            val res = api.queryRepository(name)
            val repo = (res
                .search
                .edges
                ?.getOrNull(0)
                ?.node
                ?.asUser)
                ?.repository
            val repository = if (null == repo) {
                null
            } else {
                Repository(repo.name, repo.description ?: "")
            }
            _repository.offer(repository)

            _error.offer(Error.NOPE)
        }
        catch (e: ApolloNetworkException) {
            _error.offer(Error.CONNECTION)
        }
        catch (e: ApolloHttpException) {
            _error.offer(Error.SERVER)
        }
        finally {
            _loading.offer(false)
        }
    }

}
