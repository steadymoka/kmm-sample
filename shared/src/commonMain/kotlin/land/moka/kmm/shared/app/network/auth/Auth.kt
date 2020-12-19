package land.moka.kmm.shared.app.network.auth

import land.moka.kmm.BuildKonfig

data class Auth(
    var token: String = BuildKonfig.apiKey.replace("\"", ""),
    var refreshToken: String = ""
)
