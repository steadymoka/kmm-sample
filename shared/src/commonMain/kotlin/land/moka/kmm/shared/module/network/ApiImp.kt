package land.moka.kmm.shared.module.network

import com.apollographql.apollo.*
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.flow.single
import org.kodein.di.DI
import org.kodein.di.DIAware

@ApolloExperimental
class ApiImp() : Api {

    private val apolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = "https://api.github.com/graphql",
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
                "Authorization" to "Bearer edd4db46ed6b8b63b0fdd1d53ec78e9082684cc8",
            )
        )
    )

    override suspend fun queryAboutMoka(): AboutMokaQuery.Data {
        val query = AboutMokaQuery()
        try {
            return apolloClient.query(query).execute().single().data!!
        }
        catch (e: ApolloNetworkException) {
            e.printStackTrace()
            throw Error()
        }
        catch (e: Exception) {
            throw Error()
        }
    }

    override suspend fun queryMyRepositories(endCursor: String?): MyRepositoriesQuery.Data {
        val query = MyRepositoriesQuery(Input.optional(endCursor))
        try {
            return apolloClient.query(query).execute().single().data!!
        }
        catch (e: ApolloNetworkException) {
            e.printStackTrace()
            throw Error()
        }
        catch (e: Exception) {
            throw Error()
        }
    }

    override suspend fun queryRepository(name: String): GetRepositoryQuery.Data {
        val query = GetRepositoryQuery(name)
        try {
            return apolloClient.query(query).execute().single().data!!
        }
        catch (e: ApolloNetworkException) {
            e.printStackTrace()
            throw Error()
        }
        catch (e: Exception) {
            throw Error()
        }
    }

}
