package land.moka.kmm.androidApp.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import land.moka.androidApp.databinding.LayoutRepositoryBinding
import land.moka.kmm.androidApp._Application
import land.moka.kmm.androidApp.base.BaseScopedFragment
import land.moka.kmm.shared.di.scope.RepositoryContainer

class RepositoryLayout : BaseScopedFragment(RepositoryContainer) {

    private val _view by lazy { LayoutRepositoryBinding.inflate(layoutInflater) }
    private val viewModel by lazy { _Application.container.getContainer<RepositoryContainer>().viewModel }
    private val args: RepositoryLayoutArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        initLayout()
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            viewModel.loadRepository(args.name)
        }
        return _view.root
    }

    private fun initLayout() {
        _view.textViewName.text = " "
        _view.textViewDescription.text = " "
    }

    private fun bindEvent() {
        _view.imageViewBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.repository.collect { repo ->
                if (null != repo) {
                    _view.textViewName.text = "\uD83D\uDCD3 ${repo.name}"
                    _view.textViewDescription.text = repo.description
                } else {
                    _view.textViewName.text = "삭제된 저장소입니다."
                    _view.textViewDescription.text = "-"
                }
            }
        }
    }

}
