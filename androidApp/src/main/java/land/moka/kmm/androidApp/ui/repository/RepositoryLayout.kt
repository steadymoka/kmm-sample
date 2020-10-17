package land.moka.kmm.androidApp.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import land.moka.androidApp.databinding.LayoutRepositoryBinding
import land.moka.kmm.shared.di.AppContainer
import land.moka.kmm.shared.di.scope.RepositoryScope

class RepositoryLayout : Fragment() {

    private val _view by lazy { LayoutRepositoryBinding.inflate(layoutInflater) }
    private val viewModel by lazy { AppContainer.repositoryContainer!!.viewModel }
    private val args: RepositoryLayoutArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        RepositoryScope.onCreate()

        initLayout()
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            viewModel.loadRepository(args.name)
        }
        return _view.root
    }

    override fun onDestroy() {
        RepositoryScope.onDestroy()
        super.onDestroy()
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
