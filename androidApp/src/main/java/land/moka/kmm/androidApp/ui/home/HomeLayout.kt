package land.moka.kmm.androidApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import land.moka.androidApp.databinding.LayoutHomeBinding
import land.moka.kmm.androidApp._Application
import land.moka.kmm.shared.app.viewmodel.home.HomeViewModel
import land.moka.kmm.shared.di.scope.ProfileContainer
import moka.land.base.log

class HomeLayout : Fragment() {

    private val _view by lazy { LayoutHomeBinding.inflate(layoutInflater) }
    private val viewModel by lazy { _Application.container.get<HomeViewModel>() }
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindEvent()
        return _view.root
    }

    private fun bindEvent() {
        _view.textViewHello.setOnClickListener {
            viewModel.saveUser("id test", "name test")
        }
        _view.textViewNiceToMeetYou.setOnClickListener {
            val user = viewModel.getUser()
            log("user: $user")
        }
    }

}
