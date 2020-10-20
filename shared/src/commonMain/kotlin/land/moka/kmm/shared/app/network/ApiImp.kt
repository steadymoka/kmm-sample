package land.moka.kmm.shared.app.network

import com.apollographql.apollo.*
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@ApolloExperimental
class ApiImp(
    var apolloClient: ApolloClient
) : Api {

    @InternalCoroutinesApi
    override suspend fun queryAboutMoka(): AboutMokaQuery.Data {
        val query = AboutMokaQuery()
        try {
            return apolloClient.query(query).execute().singleExt().data!!
        }
        catch (e: ApolloNetworkException) {
            e.printStackTrace()
            throw Error()
        }
        catch (e: Exception) {
            e.printStackTrace()
            throw Error()
        }
    }

    @InternalCoroutinesApi
    override suspend fun queryMyRepositories(endCursor: String?): MyRepositoriesQuery.Data {
        val query = MyRepositoriesQuery(Input.optional(endCursor))
        try {
            return apolloClient.query(query).execute().singleExt().data!!
        }
        catch (e: ApolloNetworkException) {
            e.printStackTrace()
            throw Error()
        }
        catch (e: Exception) {
            e.printStackTrace()
            throw Error()
        }
    }

    @InternalCoroutinesApi
    override suspend fun queryRepository(name: String): GetRepositoryQuery.Data {
        val query = GetRepositoryQuery(name)
        try {
            return apolloClient.query(query).execute().singleExt().data!!
        }
        catch (e: ApolloNetworkException) {
            e.printStackTrace()
            throw Error()
        }
        catch (e: Exception) {
            e.printStackTrace()
            throw Error()
        }
    }

}
