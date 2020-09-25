package land.moka.kmm.androidApp.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import land.moka.androidApp.databinding.LayoutRepositoryBinding
import land.moka.kmm.shared.module.KodeinApp
import moka.land.base.log

class RepositoryLayout : Fragment() {

    private val _view by lazy { LayoutRepositoryBinding.inflate(layoutInflater) }
    private val viewModel by lazy { KodeinApp.getRepositoryViewModel() }
    private val args: RepositoryLayoutArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initView()
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            viewModel.loadRepository(args.name)
        }
        return _view.root
    }

    override fun onDestroy() {
        KodeinApp.removeRepositoryViewModel()
        super.onDestroy()
    }

    private fun initView() {
        _view.textViewName.text = " "
        _view.textViewDescription.text = " "
    }

    private fun bindEvent() {
        _view.imageViewBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindViewModel() {
        viewModel.repository.addObserver { repo ->
            if (null != repo) {
                _view.textViewName.text = "\uD83D\uDCD3 ${repo.name}"
                _view.textViewDescription.text = repo.description
            }
            else {
                _view.textViewName.text = "삭제된 저장소입니다."
                _view.textViewDescription.text = "-"
            }
        }
    }

}
