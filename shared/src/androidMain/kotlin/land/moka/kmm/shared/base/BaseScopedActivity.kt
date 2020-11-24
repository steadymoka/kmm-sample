package land.moka.kmm.shared.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import land.moka.kmm.shared.di.AppContainer
import land.moka.kmm.shared.di.scope.index.BaseScope

open class BaseScopedActivity(var container: AppContainer, var scope: BaseScope) : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        scope.create(container)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        scope.destroy(container)
        super.onDestroy()
    }

}
