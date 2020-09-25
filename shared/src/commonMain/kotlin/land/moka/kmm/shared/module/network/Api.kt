package land.moka.kmm.shared.module.network

import com.apollographql.apollo.AboutMokaQuery
import com.apollographql.apollo.GetRepositoryQuery
import com.apollographql.apollo.MyRepositoriesQuery

interface Api {

    suspend fun queryAboutMoka(): AboutMokaQuery.Data

    suspend fun queryMyRepositories(endCursor: String?): MyRepositoriesQuery.Data

    suspend fun queryRepository(name: String): GetRepositoryQuery.Data

}
