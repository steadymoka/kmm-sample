package land.moka.kmm.shared

import platform.UIKit.UIDevice

actual class Container actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}