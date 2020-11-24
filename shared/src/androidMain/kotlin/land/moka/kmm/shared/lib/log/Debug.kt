package land.moka.kmm.shared.lib.log

import land.moka.android.BuildConfig

actual fun isDebug(): Boolean {
    return BuildConfig.DEBUG
}