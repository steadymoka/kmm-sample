package moka.land.kmm.androidApp.modules

import moka.land.kmm.androidApp.ui.home.HomeViewModel
import moka.land.kmm.androidApp.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
}
