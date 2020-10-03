package land.moka.kmm.shared.module.network

import com.apollographql.apollo.*
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@ApolloExperimental
class ApiImp(var apolloClient: ApolloClient) : Api {

    override suspend fun query() {
    }

}
