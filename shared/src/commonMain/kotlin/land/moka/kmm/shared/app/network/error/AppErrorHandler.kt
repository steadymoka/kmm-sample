package land.moka.kmm.shared.app.network.error

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import land.moka.kmm.shared.app.network.caller.Handler
import land.moka.kmm.shared.app.network.caller.Subscriber
import land.moka.kmm.shared.lib.eventbus.EventBus

open class AppErrorHandler : Handler, Subscriber, ErrorParser {

    private var errorEventBus = EventBus<AppException>()

    override fun parseException(errorCode: String, message: String): TokenException {
        when (errorCode) {
            ErrorCode.TOKEN_EXPIRED.name,
            ErrorCode.AUTH_WRONG_TOKEN.name -> {
                return TokenException.TokenExpired(message)
            }

            ErrorCode.TOKEN_INVALID.name,
            ErrorCode.AUTH_UNDEFINED_TOKEN.name,
            ErrorCode.UNAUTHORIZED.name,
            ErrorCode.INVALID_RESOURCES.name -> {
                return TokenException.TokenInvalid(message)
            }

            ErrorCode.FORBIDDEN.name,
            ErrorCode.AUTH_FORBIDDEN.name -> {
                return TokenException.TokenForbidden(message)
            }
            else -> {
                return AppException.InternalServer(message)
            }
        }
    }

    @InternalCoroutinesApi
    override fun invoke(exception: Exception) {
        errorEventBus.send(exception as AppException)
    }

    @InternalCoroutinesApi
    override fun subscribe(work: (Exception) -> Unit): Job {
        return errorEventBus.subscribe {
            work(it)
        }
    }

}
