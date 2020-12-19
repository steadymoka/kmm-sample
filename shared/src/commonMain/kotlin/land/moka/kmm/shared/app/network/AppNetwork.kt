package land.moka.kmm.shared.app.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.interceptor.BearerTokenInterceptor
import com.apollographql.apollo.interceptor.TokenProvider
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import land.moka.kmm.shared.app.network.auth.Auth

class AppNetwork(val auth: Auth) {

    private val serverUrl = "https://api.github.com/graphql"

    @ExperimentalCoroutinesApi
    @ApolloExperimental
    fun getApolloClient(): ApolloClient {
        return ApolloClient(
            networkTransport = ApolloHttpNetworkTransport(
                serverUrl = serverUrl,
                headers = mapOf(
                    "Accept" to "application/json",
                    "Content-Type" to "application/json"
                )
            ),
            interceptors = listOf(
                BearerTokenInterceptor(object : TokenProvider {
                    override suspend fun currentToken(): String {
                        return auth.token
                    }

                    override suspend fun refreshToken(previousToken: String): String {
                        return "NOPE"
                    }
                })
            )
        )
    }

}
