package land.moka.kmm.shared.di.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import land.moka.kmm.BuildKonfig
import land.moka.kmm.shared.app.network.Api
import land.moka.kmm.shared.app.network.ApiImp
import land.moka.kmm.shared.app.network.ErrorHandler
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

private const val serverUrl = "https://api.github.com/graphql"

@ExperimentalCoroutinesApi
@ApolloExperimental
val networkModule = DI.Module("network") {
    bind<ErrorHandler>() with singleton {
        ErrorHandler()
    }
    bind<ApolloClient>() with singleton {
        ApolloClient(
            networkTransport = ApolloHttpNetworkTransport(
                serverUrl = serverUrl,
                headers = mapOf(
                    "Accept" to "application/json",
                    "Content-Type" to "application/json",
                    "Authorization" to "Bearer ${BuildKonfig.apiKey.replace("\"", "")}",
                )
            )
        )
    }
    bind<Api>() with singleton {
        ApiImp(instance())
    }
}
