package land.moka.kmm.androidApp.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import land.moka.kmm.androidApp._Application
import land.moka.kmm.shared.di.scope.index.BaseScope

open class BaseScopedActivity(var scope: BaseScope) : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        scope.create(_Application.container)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        scope.destroy(_Application.container)
        super.onDestroy()
    }

}
