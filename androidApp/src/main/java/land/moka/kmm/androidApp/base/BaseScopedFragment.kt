package land.moka.kmm.androidApp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import land.moka.kmm.androidApp._Application
import land.moka.kmm.shared.di.scope.index.BaseScope

open class BaseScopedFragment(var scope: BaseScope) : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scope.create(_Application.container)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        scope.destroy(_Application.container)
        super.onDestroyView()
    }

}
