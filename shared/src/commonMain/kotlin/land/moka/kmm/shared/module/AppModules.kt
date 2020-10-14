package land.moka.kmm.shared.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import land.moka.kmm.BuildKonfig
import land.moka.kmm.shared.module.network.Api
import land.moka.kmm.shared.module.network.ApiImp
import land.moka.kmm.shared.module.network.ErrorHandler
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun appModule() = DI.Module("appModule") {
    import(networkModule)
    import(databaseModule)
}

@ExperimentalCoroutinesApi
@ApolloExperimental
val networkModule = DI.Module("network") {
    bind<ErrorHandler>() with singleton {
        ErrorHandler()
    }
    bind<ApolloClient>() with singleton {
        ApolloClient(
            networkTransport = ApolloHttpNetworkTransport(
                serverUrl = "https://api.github.com/graphql",
                headers = mapOf(
                    "Accept" to "application/json",
                    "Content-Type" to "application/json",
                    "Authorization" to "Bearer ${BuildKonfig.apiKey.replace("\"", "")}",
                )
            )
        )
    }
    bind<Api>() with singleton { ApiImp(instance(), instance()) }
}

val databaseModule = DI.Module("database") {

}
