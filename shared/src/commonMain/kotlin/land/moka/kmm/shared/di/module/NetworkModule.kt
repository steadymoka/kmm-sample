package land.moka.kmm.shared.di.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import kotlinx.coroutines.ExperimentalCoroutinesApi
import land.moka.kmm.shared.app.network.api.Api
import land.moka.kmm.shared.app.network.api.ApiImp
import land.moka.kmm.shared.app.network.auth.Auth
import land.moka.kmm.shared.app.network.AppNetwork
import land.moka.kmm.shared.app.network.error.GlobalErrorHandler
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@ExperimentalCoroutinesApi
@ApolloExperimental
val networkModule = DI.Module("network") {
    bind<Auth>() with singleton { Auth() }
    bind<AppNetwork>() with singleton { AppNetwork(instance()) }
    bind<ApolloClient>() with singleton { instance<AppNetwork>().getApolloClient() }
    bind<Api>() with singleton { ApiImp(instance()) }
    bind<GlobalErrorHandler>() with singleton { GlobalErrorHandler() }
}
