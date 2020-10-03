package land.moka.kmm.androidApp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import land.moka.androidApp.R
import land.moka.androidApp.databinding.NavigationMainBinding

class MainNavGraph : AppCompatActivity() {

    private val _view: NavigationMainBinding by lazy { NavigationMainBinding.inflate(layoutInflater) }

    private val host by lazy { supportFragmentManager.findFragmentById(R.id.host) as NavHostFragment }
    private val navController by lazy { host.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_view.root)

        initView()
    }

    private fun initView() {
    }

}
