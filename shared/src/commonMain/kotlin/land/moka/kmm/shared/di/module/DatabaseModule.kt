package land.moka.kmm.shared.di.module

import land.moka.kmm.shared.lib.preferecne.Preference
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val databaseModule = DI.Module("database") {
    bind<Preference>() with singleton {
        Preference()
    }
}
