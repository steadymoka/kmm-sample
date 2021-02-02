package land.moka.kmm.shared.app.network.error

import com.apollographql.apollo.exception.ApolloException


sealed class AppException(message: String) : TokenException(message) {

    class Network(message: String) : AppException(message = message)

    class InternalServer(message: String) : AppException(message = message)

}

sealed class TokenException(message: String) : ApolloException(message) {

    class TokenExpired(message: String) : TokenException(message = message)

    class TokenInvalid(message: String) : TokenException(message = message)

    class TokenForbidden(message: String) : TokenException(message = message)

}

enum class ErrorCode {

    // 토큰이 만료된 경우 - Refresh Token 처리
    TOKEN_EXPIRED,
    AUTH_WRONG_TOKEN,

    // 토큰이 없거나 잘못된 토큰인 경우 - 로그아웃
    TOKEN_INVALID,
    AUTH_UNDEFINED_TOKEN,
    UNAUTHORIZED,

    // 권한이 없으면 - "접근할 수 없습니다" 에러 출력
    FORBIDDEN,
    AUTH_FORBIDDEN,

    // 회원이 존재하지 않는 경우 - 로그아웃
    INVALID_RESOURCES

}
