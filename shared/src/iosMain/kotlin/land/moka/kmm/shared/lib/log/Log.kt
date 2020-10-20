package land.moka.kmm.shared.lib.log

actual class Log {

    actual companion object {
        actual fun e(message: String) {
            print(" ✏️ $message\n")
        }
    }

}