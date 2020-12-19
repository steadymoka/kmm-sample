package land.moka.kmm.shared.app.network.error

interface ErrorParser {

    fun parseException(errorCode: String, message: String): Exception

}
