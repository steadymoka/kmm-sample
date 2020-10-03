package land.moka.kmm.shared.module

import land.moka.kmm.shared.module.viewmodel.ViewModel
import org.kodein.di.DI
import org.kodein.di.direct

object KodeinApp {

    private val di = DI {
        import(appModule())
    }

    private val directDI = di.direct

    private val viewModels = hashMapOf<String, ViewModel>()

}
