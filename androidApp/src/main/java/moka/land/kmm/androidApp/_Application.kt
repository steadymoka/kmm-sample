package moka.land.kmm.androidApp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import moka.land.androidApp.BuildConfig
import moka.land.base.DEBUG
import moka.land.kmm.androidApp.modules.viewModelModule
import org.koin.android.ext.android.startKoin

@SuppressLint("StaticFieldLeak")
class _Application : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        DEBUG = BuildConfig.DEBUG

        // Koin
        startKoin(
            androidContext = this,
            modules = listOf(
                viewModelModule
            )
        )
    }

}
