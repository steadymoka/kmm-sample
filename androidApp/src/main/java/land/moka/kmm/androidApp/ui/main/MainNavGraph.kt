package land.moka.kmm.androidApp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import land.moka.androidApp.R
import land.moka.androidApp.databinding.NavigationMainBinding
import land.moka.kmm.androidApp.ui.home.HomeLayout
import land.moka.kmm.androidApp.ui.profile.ProfileLayout

class MainNavGraph : AppCompatActivity() {

    private val _view: NavigationMainBinding by lazy { NavigationMainBinding.inflate(layoutInflater) }

    private val host by lazy { supportFragmentManager.findFragmentById(R.id.host) as NavHostFragment }
    private val navController by lazy { host.navController }

    private val adapter by lazy { MainPagerAdapter(this) }

    private val homeLayout by lazy { HomeLayout() }
    private val profileLayout by lazy { ProfileLayout() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_view.root)

        initView()
    }

    private fun initView() {
        adapter.addItems(homeLayout, profileLayout)

        _view.viewPager.run {
            adapter = this@MainNavGraph.adapter
            offscreenPageLimit = 1
            isUserInputEnabled = false
        }

        _view.bottomNavigation.setOnNavigationItemSelectedListener {
            if (_view.viewPager.currentItem == adapter.getItemInt(it.itemId)) {
                when (_view.viewPager.currentItem) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }
            _view.viewPager.setCurrentItem(adapter.getItemInt(it.itemId), false)
            true
        }
    }

}
