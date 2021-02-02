package land.moka.kmm.shared.di.module

import land.moka.kmm.shared.app.preference.MokaPref
import land.moka.kmm.shared.lib.preferecne.Preference
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val appModule = DI.Module("app") {
    bind<MokaPref>() with singleton {
        MokaPref()
    }
}
