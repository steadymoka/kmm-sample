package land.moka.kmm.shared.app.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String = "",
    var username: String = ""
)