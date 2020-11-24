package land.moka.kmm.shared.lib.log

actual class Log {

    actual companion object {
        actual fun print(message: String) {
            kotlin.io.print(" ✏️ $message\n")
        }
    }

}