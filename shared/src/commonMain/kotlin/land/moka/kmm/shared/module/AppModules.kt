package land.moka.kmm.shared.module

import com.apollographql.apollo.api.ApolloExperimental
import land.moka.kmm.shared.module.network.Api
import land.moka.kmm.shared.module.network.ApiImp
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun appModule() = DI.Module("appModule") {
    import(networkModule)
    import(databaseModule)
}

@ApolloExperimental
val networkModule = DI.Module("network") {
    bind<Api>() with singleton { ApiImp() }
}

val databaseModule = DI.Module("database") {

}
