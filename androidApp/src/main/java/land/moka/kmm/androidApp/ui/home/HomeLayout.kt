package land.moka.kmm.androidApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import land.moka.androidApp.databinding.LayoutHomeBinding

class HomeLayout : Fragment() {

    private val _view by lazy { LayoutHomeBinding.inflate(layoutInflater) }
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindEvent()
        return _view.root
    }

    private fun bindEvent() {
    }

}
