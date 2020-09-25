package land.moka.kmm.androidApp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import land.moka.androidApp.R
import land.moka.androidApp.databinding.LayoutProfileBinding
import land.moka.kmm.androidApp.ui.main._BlankFragmentDirections
import land.moka.kmm.androidApp.ui.profile.adapter.OverviewAdapter
import land.moka.kmm.androidApp.ui.profile.adapter.RepositoryAdapter
import land.moka.kmm.androidApp.util.load
import land.moka.kmm.shared.lib.livedata.combineWith
import land.moka.kmm.shared.module.KodeinApp
import land.moka.kmm.shared.module.viewmodel.profile.ProfileViewModel
import moka.land.base.gone
import moka.land.base.goneFadeOut
import moka.land.base.visible
import moka.land.base.visibleFadeIn


class ProfileLayout : Fragment() {

    private val _view by lazy { LayoutProfileBinding.inflate(layoutInflater) }

    private val viewModel: ProfileViewModel by lazy { KodeinApp.getProfileViewModel() }

    private val overviewAdapter by lazy { OverviewAdapter() }
    private val repositoryAdapter by lazy { RepositoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        init()
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            viewModel.loadProfileData()
        }
        return _view.root
    }

    override fun onDestroy() {
        KodeinApp.removeProfileViewModel()
        super.onDestroy()
    }

    private fun init() {
        viewModel.selectedTab.value = ProfileViewModel.Tab.Overview

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
                        viewModel.selectedTab.value = ProfileViewModel.Tab.Overview
                    }
                    ProfileViewModel.Tab.Repositories -> {
                        lifecycleScope.launch {
                            _view.recyclerViewRepositories.scrollToPosition(0)

                            viewModel.selectedTab.value = ProfileViewModel.Tab.Repositories
                            viewModel.myRepositoryList.value = arrayListOf()
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

                if (!viewModel.loading.value) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= repositoryAdapter.itemCount - 2) {
                        lifecycleScope.launch {
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
        viewModel.profile.addObserver {
            _view.textViewName.text = it.name
            _view.textViewBio.text = it.bio
            _view.textViewStatus.text = "\"${it.status?.message}\""
            _view.imageViewProfileImage.load(requireActivity(), "${it.avatarUrl}")

            _view.run {
                listOf(view01, view02, view03).forEach { it.gone() }
            }
        }

        viewModel.selectedTab.addObserver {
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

        combineWith(viewModel.pinnedList, viewModel.organizerList) { pinnedList, organizerList ->
            if (null == pinnedList || null == organizerList) {
                return@combineWith
            }
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
        }.addObserver { }

        viewModel.myRepositoryList.addObserver { repoList ->
            if (repoList.isEmpty()) {
                repositoryAdapter.items.clear()
                repositoryAdapter.notifyDataSetChanged()
                return@addObserver
            }
            repositoryAdapter.replaceItems(repoList.map { RepositoryAdapter.Data(it) })
        }

        viewModel.loading.addObserver {
            when (viewModel.selectedTab.value) {
                ProfileViewModel.Tab.Overview -> {
                }
                ProfileViewModel.Tab.Repositories -> {
                    if (it) {
                        repositoryAdapter.showFooterLoading()
                    }
                    else {
                        repositoryAdapter.hideFooterLoading()
                    }
                }
            }
        }

        viewModel.error.addObserver {
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
