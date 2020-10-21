package land.moka.kmm.androidApp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import land.moka.androidApp.R
import land.moka.androidApp.databinding.LayoutProfileBinding
import land.moka.kmm.androidApp._Application
import land.moka.kmm.androidApp.ui.main._BlankFragmentDirections
import land.moka.kmm.androidApp.ui.profile.adapter.OverviewAdapter
import land.moka.kmm.androidApp.ui.profile.adapter.RepositoryAdapter
import land.moka.kmm.androidApp.util.load
import land.moka.kmm.shared.app.viewmodel.profile.ProfileViewModel
import land.moka.kmm.shared.di.scope.ProfileContainer
import land.moka.kmm.shared.model.Repository
import moka.land.base.*

class ProfileLayout : Fragment() {

    private val _view by lazy { LayoutProfileBinding.inflate(layoutInflater) }

    private val viewModel by lazy { _Application.container.getContainer<ProfileContainer>().viewModel }

    private val overviewAdapter by lazy { OverviewAdapter() }
    private val repositoryAdapter by lazy { RepositoryAdapter() }

    @InternalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        ProfileContainer.onCreate(_Application.container)

        initLayout()
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            viewModel.loadProfileData()
        }
        return _view.root
    }

    override fun onDestroy() {
        ProfileContainer.onDestroy(_Application.container)
        super.onDestroy()
    }

    private fun initLayout() {
        viewModel.selectTab(ProfileViewModel.Tab.Overview)

        _view.recyclerViewOverview.showPlaceHolder(R.layout.view_overview_placeholder)
        _view.recyclerViewOverview.adapter = overviewAdapter
        _view.recyclerViewRepositories.adapter = repositoryAdapter
    }

    private fun bindEvent() {
        _view.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (ProfileViewModel.Tab.get(tab?.position ?: 0)) {
                    ProfileViewModel.Tab.Overview -> {
                        viewModel.selectTab(ProfileViewModel.Tab.Overview)
                    }
                    ProfileViewModel.Tab.Repositories -> {
                        lifecycleScope.launch {
                            _view.recyclerViewRepositories.scrollToPosition(0)

                            viewModel.selectTab(ProfileViewModel.Tab.Repositories)
                            viewModel.clearMyRepository()
                            viewModel.reloadRepositories()
                        }
                    }
                }
            }
        })

        _view.recyclerViewRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

                lifecycleScope.launch {
                    if (!viewModel.loading.first()) {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= repositoryAdapter.itemCount - 2) {
                            viewModel.loadRepositories()
                        }
                    }
                }
            }
        })

        overviewAdapter.onClickItem = {
            if (it.type == OverviewAdapter.Type.PINNED && null != it.repository) {
                val direction = _BlankFragmentDirections.goRepository(it.repository!!.name)
                findNavController().navigate(direction)
            }
        }

        repositoryAdapter.onClickItem = {
            val direction = _BlankFragmentDirections.goRepository(it.repository.name)
            findNavController().navigate(direction)
        }
    }

    private fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.profile.collect {
                _view.textViewName.text = it.name
                _view.textViewBio.text = it.bio
                _view.textViewStatus.text = "\"${it.status?.message}\""
                _view.imageViewProfileImage.load(requireActivity(), "${it.avatarUrl}")

                _view.run {
                    listOf(view01, view02, view03).forEach { it.gone() }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.selectedTab.collect {
                when (it) {
                    ProfileViewModel.Tab.Overview -> {
                        _view.recyclerViewOverview.visibleFadeIn(150)
                        _view.recyclerViewRepositories.goneFadeOut(150)
                    }
                    ProfileViewModel.Tab.Repositories -> {
                        _view.recyclerViewOverview.goneFadeOut(150)
                        _view.recyclerViewRepositories.visibleFadeIn(150)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.pinnedList.zip(viewModel.organizerList) { pinnedList, organizerList ->
                _view.recyclerViewOverview.hidePlaceHolder(200)

                val items = pinnedList
                    .asSequence()
                    .map { OverviewAdapter.Data(type = OverviewAdapter.Type.PINNED, repository = it) }
                    .plus(
                        listOf(OverviewAdapter.Data(type = OverviewAdapter.Type.ORGANIZER_SECTION))
                    )
                    .plus(
                        organizerList
                            .map { OverviewAdapter.Data(type = OverviewAdapter.Type.ORGANIZER, organizer = it) }
                            .toList()
                    )
                    .toList()
                overviewAdapter.setItems(items)
            }.collect { }
        }

        lifecycleScope.launch {
            viewModel.myRepositoryList.collect { repoList: ArrayList<Repository>? ->
                if (null == repoList) {
                    repositoryAdapter.items.clear()
                    repositoryAdapter.notifyDataSetChanged()
                    return@collect
                }
                val list = repositoryAdapter.items + repoList.map { RepositoryAdapter.Data(it) }
                repositoryAdapter.replaceItems(list)
            }
        }

        lifecycleScope.launch {
            viewModel.loading.collect {
                when (ProfileViewModel.Tab.get(_view.tabLayout.selectedTabPosition)) {
                    ProfileViewModel.Tab.Overview -> {
                    }
                    ProfileViewModel.Tab.Repositories -> {
                        if (it) {
                            repositoryAdapter.showFooterLoading()
                        } else {
                            repositoryAdapter.hideFooterLoading()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                when (it) {
                    ProfileViewModel.Error.CONNECTION -> {
                        _view.recyclerViewOverview.hidePlaceHolder(200)

                        _view.textViewError.visible()
                        _view.textViewError.text = "인터넷 연결을 확인해주세요 :D"
                    }
                    ProfileViewModel.Error.SERVER -> {
                        _view.recyclerViewOverview.hidePlaceHolder(200)

                        _view.textViewError.visible()
                        _view.textViewError.text = "예상치 못한 에러입니다 :(\n\napikey.properties 파일의 GitHub api key 를 확인해주세요\nread:org 권한을 포함 하여야 합니다."
                    }
                    ProfileViewModel.Error.NOPE -> {
                        _view.textViewError.gone()
                    }
                }
            }
        }
    }

}
