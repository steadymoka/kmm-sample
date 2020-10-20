package land.moka.kmm.shared.lib.log

actual fun isDebug(): Boolean {
    return Platform.isDebugBinary
}