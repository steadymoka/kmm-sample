package moka.land.kmm.androidApp.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import moka.land.androidApp.databinding.LayoutRepositoryBinding

class RepositoryLayout : Fragment() {

    private val _view by lazy { LayoutRepositoryBinding.inflate(layoutInflater) }
    private val args: RepositoryLayoutArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            // todo: load data
        }
        return _view.root
    }

    private fun bindEvent() {
        _view.imageViewBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindViewModel() {
//        viewModel.repository.observe(viewLifecycleOwner, Observer { repo ->
//            if (null != repo) {
//                _view.textViewName.text = "\uD83D\uDCD3 ${repo.name()}"
//                _view.textViewDescription.text = repo.description()
//            }
//            else {
//                _view.textViewName.text = "삭제된 저장소입니다."
//                _view.textViewDescription.text = "-"
//            }
//        })
    }

}
