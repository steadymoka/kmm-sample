package land.moka.kmm.shared.lib.log

import android.util.Log

actual class Log {

    actual companion object {
        actual fun print(message: String) {
            Log.wtf("✏️✏️", message)
        }
    }

}