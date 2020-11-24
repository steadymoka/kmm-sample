package land.moka.kmm.shared.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import land.moka.kmm.shared.di.AppContainer
import land.moka.kmm.shared.di.scope.index.BaseScope

open class BaseScopedFragment(var container: AppContainer, var scope: BaseScope) : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scope.create(this.container)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        scope.destroy(this.container)
        super.onDestroyView()
    }

}
